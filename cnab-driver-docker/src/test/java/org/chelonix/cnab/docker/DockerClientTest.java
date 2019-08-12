package org.chelonix.cnab.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.Image;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DockerClientTest {

    @Test
    public void testDockerClient() throws Exception {
        DockerClient docker = DefaultDockerClient.fromEnv().build();

        List<Image> images = docker.listImages();

        for (Image image: images) {
            System.out.println(image);
        }
    }
}
