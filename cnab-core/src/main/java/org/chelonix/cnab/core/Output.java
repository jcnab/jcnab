
package org.chelonix.cnab.core;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A value that is produced by running an invocation image
 * 
 */
public class Output {

    /**
     * An optional exhaustive list of actions handling this parameter
     *
     */
    private List<String> applyTo;

    /**
     * The name of a definition that describes the schema structure of this parameter
     *
     */
    private String definition;

    private String description;

    /**
     * The path inside of the invocation image where output will be written
     *
     */
    private String path;

    /**
     * Indicates whether this parameter must be supplied. By default, parameters are optional
     *
     */
    private boolean required;

    public Output() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Output.class.getSimpleName() + "[", "]")
                .add("applyTo=" + applyTo)
                .add("definition='" + definition + "'")
                .add("description='" + description + "'")
                .add("path='" + path + "'")
                .add("required=" + required)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Output output = (Output) o;
        return required == output.required &&
                Objects.equals(applyTo, output.applyTo) &&
                Objects.equals(definition, output.definition) &&
                Objects.equals(description, output.description) &&
                Objects.equals(path, output.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applyTo, definition, description, path, required);
    }
}
