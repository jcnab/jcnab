
package org.chelonix.cnab.core;


import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;

/**
 * A parameter that can be passed into the invocation image
 * 
 */
public class Parameter {

    /**
     * An optional exhaustive list of actions handling this parameter
     *
     */
    @JsonbProperty("applyTo")
    private List<String> applyTo;

    /**
     * The name of a definition that describes the schema structure of this parameter
     *
     */
    @JsonbProperty("definition")
    private String definition;

    @JsonbProperty("description")
    private String description;

    @JsonbProperty("destination")
    private Parameter.Destination destination;

    /**
     * Indicates whether this parameter must be supplied. By default, parameters are optional
     *
     */
    @JsonbProperty("required")
    private boolean required;

    public static class Destination {

        /**
         * The environment variable name, such as MY_VALUE, in which the parameter value is stored
         *
         */
        @JsonbProperty("env")
        private String env;

        /**
         * The path inside of the invocation image where parameter data is mounted
         *
         */
        @JsonbProperty("path")
        private String path;

        public Destination() {
        }

        public String getEnv() {
            return env;
        }

        public String getPath() {
            return path;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Destination.class.getSimpleName() + "[", "]")
                    .add("env='" + env + "'")
                    .add("path='" + path + "'")
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Destination that = (Destination) o;
            return Objects.equals(env, that.env) &&
                    Objects.equals(path, that.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(env, path);
        }
    }

    public Parameter() {
    }

    public List<String> getApplyTo() {
        return applyTo;
    }

    public void setApplyTo(List<String> applyTo) {
        this.applyTo = applyTo;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Parameter.class.getSimpleName() + "[", "]")
                .add("applyTo=" + applyTo)
                .add("definition='" + definition + "'")
                .add("description='" + description + "'")
                .add("destination=" + destination)
                .add("required=" + required)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return required == parameter.required &&
                Objects.equals(applyTo, parameter.applyTo) &&
                Objects.equals(definition, parameter.definition) &&
                Objects.equals(description, parameter.description) &&
                Objects.equals(destination, parameter.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applyTo, definition, description, destination, required);
    }
}
