package org.chelonix.cnab.driver;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.chelonix.cnab.action.Action;
import org.chelonix.cnab.core.Bundle;

public class DriverOperation {

    private Bundle bundle;
    private String action;
    private String claim;
    private String invocationImage;
    private Map<Path, String> files = new HashMap<>();
    private Map<String, String> environment = new HashMap<>();

    public static Builder newBuilder(Bundle bundle, String action, String claim) {
        return new Builder(bundle, action, claim);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public String getAction() {
        return action;
    }

    public String getClaim() {
        return claim;
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

    public static class Builder {

        private DriverOperation operation;

        Builder(Bundle bundle, String action, String claim) {
            operation = new DriverOperation();
            operation.bundle = bundle;
            operation.action = action;
            operation.claim = claim;
        }

        public Builder withValue(String name, String value) {
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

        private void validate() throws InvalidOperationException {
            if (!operation.getBundle().getActions().keySet().contains(operation.getAction()) &&
                    !Action.isBuiltIn(operation.getAction()))
            {
                throw new InvalidOperationException(String.format("Action %s is not defined in the bundle",
                        operation.getAction()));
            }
        }

        public DriverOperation build() throws InvalidOperationException {
            validate();
            return operation;
        }
    }
}
