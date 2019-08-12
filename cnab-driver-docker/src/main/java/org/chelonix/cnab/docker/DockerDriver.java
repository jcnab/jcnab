package org.chelonix.cnab.docker;

import com.google.common.io.ByteStreams;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;
import org.chelonix.cnab.driver.Output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DockerDriver implements Driver {

    private OutputStream outStream = ByteStreams.nullOutputStream();
    private OutputStream errStream = ByteStreams.nullOutputStream();
    private boolean removeContainer = true;

    public void setContainerOut(OutputStream writer) {
        this.outStream = writer;
    }

    public void setContainerErr(OutputStream writer) {
        this.errStream = writer;
    }

    public void removeContainerOnExit(boolean removeContainer) {
        this.removeContainer = removeContainer;
    }

    @Override
    public Output run(DriverOperation operation) throws Exception {
        DockerClient docker = DefaultDockerClient.fromEnv().build();
        //docker.pull(operation.getInvocationImage());
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
        //ContainerInfo info = docker.inspectContainer(id);
        docker.copyToContainer(in, id, "/");
        docker.startContainer(id);
        LogStream stream = docker.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr());
        stream.attach(outStream, errStream);
        docker.waitContainer(id);
        if (removeContainer) {
            docker.removeContainer(id);
        }
        return new Output();
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
