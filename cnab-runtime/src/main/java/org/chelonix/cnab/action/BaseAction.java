package org.chelonix.cnab.action;

import org.chelonix.cnab.core.Bundle;
import org.chelonix.cnab.core.Claim;
import org.chelonix.cnab.core.Parameter;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;
import org.chelonix.cnab.driver.InvalidOperationException;
import org.leadpony.justify.api.JsonSchema;

import javax.json.JsonString;
import javax.json.JsonValue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public abstract class BaseAction implements Action {

    private Driver driver;
    private Claim claim;

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

    DriverOperation newOperation() throws ActionException{
        Bundle bundle = claim.getBundle();
        DriverOperation.Builder builder = DriverOperation.newBuilder(bundle, getName(), claim.getName());
        builder.withInvocationImage(bundle.getInvocationImages().get(0).getImage()); // FIXME
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
                builder.withValue(dest.getEnv(), value);
            }

        }

        /* CNAB required environment variables */
        builder.withValue("CNAB_INSTALLATION_NAME", claim.getName());
        builder.withValue("CNAB_ACTION", getName());
        builder.withValue("CNAB_BUNDLE_NAME", bundle.getName());
        builder.withValue("CNAB_BUNDLE_VERSION", bundle.getVersion());

        try {
            return builder.build();
        } catch (InvalidOperationException ioe) {
            // Fixme
            return null;
        }
    }
}
