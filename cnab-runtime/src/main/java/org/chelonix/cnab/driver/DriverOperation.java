package org.chelonix.cnab.driver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DriverOperation {

    private static final Path OUTPUT_PREFIX = Paths.get("/", "cnab", "app", "outputs");

    private String invocationImage;
    private Map<Path, String> files = new HashMap<>();
    private Map<String, String> environment = new HashMap<>();
    private Map<String, Path> outputs = new HashMap<>();

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getInvocationImage() {
        return invocationImage;
    }

    public Map<Path, String> getFiles() {
        return files;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public Map<String, Path> getOutputs() {
        return outputs;
    }

    public static class Builder {

        private DriverOperation operation;

        Builder() {
            operation = new DriverOperation();
        }

        public Builder withEnv(String name, String value) {
            operation.environment.put(name, value);
            return this;
        }

        public Builder withFile(Path path, String value) {
            operation.files.put(path, value);
            return this;
        }

        public Builder withInvocationImage(String image) {
            operation.invocationImage = image;
            return this;
        }

        public Builder withOutput(String name, Path path) {
            operation.outputs.put(name, path);
            return this;
        }

        public DriverOperation build() {
            return operation;
        }
    }
}
