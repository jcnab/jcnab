package org.chelonix.cnab.client;

import static org.assertj.core.api.Assertions.*;

import com.google.common.io.Resources;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import org.chelonix.cnab.action.Install;
import org.chelonix.cnab.core.Bundle;
import org.chelonix.cnab.core.BundleReader;
import org.chelonix.cnab.core.Claim;
import org.chelonix.cnab.docker.DockerDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class ClientTest {

    @BeforeAll
    public static void setUp() throws Exception {
        Path dockerfile = Paths.get(ClientTest.class.getResource("Dockerfile").getPath());
        Path ctxDirectory = dockerfile.getParent();
        DockerClient docker = DefaultDockerClient.fromEnv().build();
        docker.build(ctxDirectory,
                "sirot/jcnab-invoc:0.1.0",
                DockerClient.BuildParam.dockerfile(Paths.get("Dockerfile")),
                DockerClient.BuildParam.noCache());
    }

    @Test
    public void testDefaultValueParameter() throws Exception {
        // Given
        Bundle bundle = BundleReader.read(this.getClass().getResourceAsStream("simple-bundle.json"));
        // When
        Claim claim = new Claim();
        claim.setBundle(bundle);
        claim.setName("my-claim");
        claim.setCreated(LocalDateTime.now());
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        driver.removeContainerOnExit(false);
        Install installAction = new Install(claim, driver);
        installAction.run();
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        String expected = Resources.toString(getClass().getResource("default-value/output.txt"), StandardCharsets.UTF_8);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEnvVarParameter() throws Exception {
        // Given
        Bundle bundle = BundleReader.read(this.getClass().getResourceAsStream("simple-bundle.json"));
        // When
        Claim claim = new Claim();
        claim.setBundle(bundle);
        claim.setName("my-claim");
        claim.withParameter("param", "some custom value");
        claim.setCreated(LocalDateTime.now());
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        driver.removeContainerOnExit(false);
        Install installAction = new Install(claim, driver);
        installAction.run();
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        String expected = Resources.toString(getClass().getResource("env-var/output.txt"), StandardCharsets.UTF_8);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFileVarParameter() throws Exception {
        // Given
        Bundle bundle = BundleReader.read(this.getClass().getResourceAsStream("simple-bundle.json"));
        // When
        Claim claim = new Claim();
        claim.setBundle(bundle);
        claim.setName("my-claim");
        claim.withParameter("file", "some values from a file\n");
        claim.setCreated(LocalDateTime.now());
        DockerDriver driver = new DockerDriver();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        driver.setContainerOut(output);
        driver.removeContainerOnExit(false);
        Install installAction = new Install(claim, driver);
        installAction.run();
        // Then
        String actual = new String(output.toByteArray(), "UTF-8");
        String expected = Resources.toString(getClass().getResource("file-var/output.txt"), StandardCharsets.UTF_8);
        assertThat(actual).isEqualTo(expected);
    }
}
