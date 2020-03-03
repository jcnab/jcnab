package org.chelonix.cnab.docker;

import com.google.common.io.ByteStreams;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverException;
import org.chelonix.cnab.driver.DriverOperation;
import org.chelonix.cnab.driver.Output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class DockerDriver implements Driver {

    private OutputStream outStream = System.out;
    private OutputStream errStream = System.err;
    private boolean removeContainer = true;
    private boolean pullAlways= false;

    /**
     * Set the container stdout target. Default is System.out
     * @param writer the container stdout
     */
    public void setContainerOut(OutputStream writer) {
        this.outStream = writer;
    }

    /**
     * Set the container stderr target. Default is System.err
     * @param writer the container stderr
     */
    public void setContainerErr(OutputStream writer) {
        this.errStream = writer;
    }

    public void setPullAlways(boolean pullAlways) {
        this.pullAlways = pullAlways;
    }

    public void removeContainerOnExit(boolean removeContainer) {
        this.removeContainer = removeContainer;
    }

    @Override
    public Output run(DriverOperation operation) throws Exception {
        DockerClient docker = DefaultDockerClient.fromEnv().build();
        if (pullAlways) {
            docker.pull(operation.getInvocationImage());
        }
        List<String> env = new ArrayList<>();
        for (Map.Entry<String, String> envVar: operation.getEnvironment().entrySet()) {
            env.add(String.format("%s=%s", envVar.getKey(), envVar.getValue()));
        }

        InputStream in = generateTar(operation.getFiles());

        ContainerConfig containerConfig = ContainerConfig.builder()
                .image(operation.getInvocationImage())
                .attachStdout(true)
                .attachStderr(true)
                .env(env)
                .cmd("/cnab/app/run")
                .build();

        ContainerCreation creation = docker.createContainer(containerConfig);
        String id = creation.id();

        Executors.newSingleThreadExecutor().submit((Callable<Void>) () -> {
            docker.attachContainer(id,
                    DockerClient.AttachParameter.LOGS,
                    DockerClient.AttachParameter.STDOUT,
                    DockerClient.AttachParameter.STDERR,
                    DockerClient.AttachParameter.STREAM)
                    .attach(outStream, errStream);
            return null;
        });

        docker.copyToContainer(in, id, "/");

        docker.startContainer(id);

        Output.Builder outputBuilder = new Output.Builder();
        for (Map.Entry<String, Path> output: operation.getOutputs().entrySet()) {
            outputBuilder.withOutput(output.getKey(), fetchOutput(docker, id, output.getValue()));
        };

        docker.waitContainer(id);

        if (removeContainer) {
            docker.removeContainer(id);
        }

        return outputBuilder.build();
    }

    private InputStream fetchOutput(DockerClient docker, String containerID, Path path) throws Exception {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream(); // FIXME Write in a temp file
        try (final TarArchiveInputStream tarStream = new TarArchiveInputStream(
                docker.archiveContainer(containerID, path.toString())))
        {
            TarArchiveEntry entry;
            while ((entry = tarStream.getNextTarEntry()) != null) {
                if (entry.isDirectory()) {
                    throw new DriverException(String.format("Output %s is a directory", entry.getName()));
                } else {
                    int r;
                    while ((r = tarStream.read(buf)) != -1) {
                        out.write(buf, 0, r);
                    }
                }
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private InputStream generateTar(Map<Path, String> files) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); // FIXME write the tar on disk to avoid potential OutOfMemoryError
        try (final GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(out);
             final TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzipOut)) {
            tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
            tarOut.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_POSIX);
            for (Map.Entry<Path, String> entry: files.entrySet()) {
                byte[] value = entry.getValue().getBytes(StandardCharsets.UTF_8);
                File file = entry.getKey().toFile();
                TarArchiveEntry e = new TarArchiveEntry(file);
                e.setName(file.getAbsolutePath());
                e.setMode(0644);
                e.setSize(value.length);
                tarOut.putArchiveEntry(e);
                tarOut.write(value);
                tarOut.closeArchiveEntry();
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public boolean support(String imageType) {
        return imageType.equalsIgnoreCase("oci") || imageType.equalsIgnoreCase("docker");
    }
}
