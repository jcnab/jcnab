package org.chelonix.cnab.docker;

import static org.assertj.core.api.Assertions.*;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import org.chelonix.cnab.driver.DriverOperation;
import org.chelonix.cnab.driver.Output;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DockerDriverTest {

    private static final String[][] IMAGES = {
            { "simple/Dockerfile", "sirot/jcnab-test:0.1.0" },
            { "env/Dockerfile", "sirot/jcnab-test-env:0.1.0" },
            { "file/Dockerfile", "sirot/jcnab-test-file:0.1.0" },
            { "out-err/Dockerfile", "sirot/jcnab-test-out-err:0.1.0" },
            { "output/Dockerfile", "sirot/jcnab-test-output:0.1.0" }
    };

    public static void buildImage(String path, String image) throws Exception {
            Path dockerfile = Paths.get(DockerDriverTest.class.getResource(path).getPath());
            Path ctxDirectory = dockerfile.getParent();
            DockerClient docker = DefaultDockerClient.fromEnv().build();
            docker.build(ctxDirectory,
                    image,
                    DockerClient.BuildParam.dockerfile(Paths.get("Dockerfile")),
                    DockerClient.BuildParam.noCache());
    }

    @Test
    public void testRunDockerImage() throws Exception {
        // Given
        buildImage("simple/Dockerfile", "sirot/jcnab-test:0.1.0");
        DriverOperation.Builder builder = DriverOperation.newBuilder();
        builder.withInvocationImage("sirot/jcnab-test:0.1.0");
        DriverOperation op = builder.build();
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        // When
        driver.run(op);
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        assertThat(actual).isEqualTo("OK\n");
    }

    @Test
    public void testPassEnvVars() throws Exception {
        // Given
        buildImage("env/Dockerfile", "sirot/jcnab-test-env:0.1.0");
        DriverOperation.Builder builder = DriverOperation.newBuilder();
        builder.withInvocationImage("sirot/jcnab-test-env:0.1.0")
            .withEnv("ENV_1", "foo")
            .withEnv("ENV_2", "bar");
        DriverOperation op = builder.build();
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        // When
        driver.run(op);
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        assertThat(actual).isEqualTo("foo\nbar\n");
    }

    @Test
    public void testAddFiles() throws Exception {
        // Given
        buildImage("file/Dockerfile", "sirot/jcnab-test-file:0.1.0");
        DriverOperation.Builder builder = DriverOperation.newBuilder();
        builder.withInvocationImage("sirot/jcnab-test-file:0.1.0")
            .withFile(Paths.get("/etc/foo"), "bar");
        DriverOperation op = builder.build();
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        // When
        driver.run(op);
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        assertThat(actual).isEqualTo("bar");
    }

    @Test
    public void testOutErrRedirect() throws Exception {
        // Given
        buildImage("out-err/Dockerfile", "sirot/jcnab-test-out-err:0.1.0");
        DriverOperation.Builder builder = DriverOperation.newBuilder();
        builder.withInvocationImage("sirot/jcnab-test-out-err:0.1.0");
        DriverOperation op = builder.build();
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        driver.setContainerOut(stdout);
        driver.setContainerErr(stderr);
        driver.removeContainerOnExit(false);
        // When
        driver.run(op);
        // Then
        String actualOut = new String(stdout.toByteArray(), "UTF-8");
        assertThat(actualOut).isEqualTo("OK out\n");
        String actualErr = new String(stderr.toByteArray(), "UTF-8");
        assertThat(actualErr).isEqualTo("OK err\n");
    }

    @Test
    public void testOutput() throws Exception {
        try {
            // Given
            buildImage("output/Dockerfile", "sirot/jcnab-test-output:0.1.0");
            DriverOperation.Builder builder = DriverOperation.newBuilder();
            builder.withInvocationImage("sirot/jcnab-test-output:0.1.0")
                    .withOutput("out1", Paths.get("/cnab/app/outputs/1.txt"))
                    .withOutput("out2", Paths.get("/cnab/app/outputs/2.txt"));
            DriverOperation op = builder.build();
            DockerDriver driver = new DockerDriver();
            driver.removeContainerOnExit(false);
            // When
            Output out = driver.run(op);
            // Then
            assertThat(out.getData("out1")).hasContent("OK output 1");
            assertThat(out.getData("out2")).hasContent("OK output 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
