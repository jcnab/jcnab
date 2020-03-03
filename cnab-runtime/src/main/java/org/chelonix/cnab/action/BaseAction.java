package org.chelonix.cnab.action;

import org.chelonix.cnab.core.*;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;
import org.leadpony.justify.api.JsonSchema;

import javax.json.JsonString;
import javax.json.JsonValue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseAction<T extends BaseAction<T>> implements Action {

    private static final Path OUTPUT_PREFIX = Paths.get("/", "cnab", "app", "outputs");

    private Driver driver;
    private Claim claim;
    private Map<String, String> creds = new HashMap<>();

    protected BaseAction(Claim claim, Driver driver) {
        this.claim = claim;
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public Claim getClaim() {
        return claim;
    }

    public T withCredentials(String key, String value) {
        creds.put(key, value);
        return (T) this;
    }

    DriverOperation newOperation() throws ActionException {
        Bundle bundle = claim.getBundle();
        if (!bundle.getActions().contains(getName()) && !Action.isBuiltIn(getName())) {
            throw new ActionException("Action %s is not defined in the bundle", getName());
        }

        DriverOperation.Builder builder = DriverOperation.newBuilder();
        builder.withInvocationImage(bundle.getInvocationImages().get(0).getImage()); // FIXME
        try {
            builder.withFile(Paths.get("/", "cnab", "bundle.json"), BundleWriter.writeCanonical(bundle));
        } catch (BundleIOException bioe) {
            throw new ActionException("Bundle encoding failed", bioe);
        }
        for(Map.Entry<String, Parameter> param: bundle.getParameters().entrySet()) {
            JsonSchema schema = bundle.getDefinitions().get(param.getValue().getDefinition());
            boolean required = false;
            if (schema.getKeywordValue("required", JsonValue.FALSE) == JsonValue.TRUE) {
                required = true;
            }
            Optional<String> defaultValue = Optional.empty();
            if (schema.defaultValue() != null) {
                if (schema.defaultValue().getValueType() == JsonValue.ValueType.STRING) {
                    defaultValue = Optional.of(((JsonString)schema.defaultValue()).getString());
                } else {
                    defaultValue = Optional.of(schema.defaultValue().toString());
                }
            }
            Optional<String> actualValue = Optional.empty();
            if (claim.getParameters().containsKey(param.getKey())) {
                actualValue = Optional.of(claim.getParameters().get(param.getKey()));
            }

            String value = actualValue.orElse(required ? defaultValue.orElseThrow(
                    () -> new ActionException("Missing value for required parameter: %s", param.getKey())) :
                    defaultValue.orElse(""));

            Parameter.Destination dest = param.getValue().getDestination();
            if (dest == null) {
                // FIXME
                continue;
            }
            if (dest.getPath() != null) {
                Path path = Paths.get(dest.getPath());
                if (!path.isAbsolute()) {
                    throw new ActionException("Path %s is not absolute", path.toString());
                }
                builder.withFile(path, value);
            }
            if (dest.getEnv() != null) {
                builder.withEnv(dest.getEnv(), value);
            }
        }

        for (Credential cred: bundle.getCredentials()) {
            String name = cred.getName();
            if (!creds.containsKey(name)) {
                if (cred.isRequired()) {
                    throw new ActionException("Credential %s is required", cred.getName());
                }
                continue;
            }
            if (cred.getEnv() != null) {
                builder.withEnv(cred.getEnv(), creds.get(name));
            }
            if (cred.getPath() != null) {
                builder.withFile(Paths.get(cred.getPath()), creds.get(name));
            }
        }

        for (Output output: bundle.getOutputs()) {
            Path path = Paths.get(output.getPath());
            if (!path.startsWith(OUTPUT_PREFIX)) {
                throw new ActionException("Output path %s is not a subpath of /cnab/app/output", path);
            }
            builder.withOutput(output.getName(), path);
        }

        /* CNAB required environment variables */
        builder.withEnv("CNAB_INSTALLATION_NAME", claim.getName());
        builder.withEnv("CNAB_ACTION", getName());
        builder.withEnv("CNAB_BUNDLE_NAME", bundle.getName());
        builder.withEnv("CNAB_BUNDLE_VERSION", bundle.getVersion());

        return builder.build();
    }
}
