
package org.chelonix.cnab.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A value that is produced by running an invocation image
 * 
 */
public class Output implements MapOf.Named {

    @JsonbTransient
    private String name;

    /**
     * Restricts this output to a given list of actions. If empty or missing, applies to all actions
     *
     */
    @JsonbProperty("applyTo")
    private List<String> applyTo;

    /**
     * TThe name of a definition schema that is used to validate the output content
     *
     */
    @JsonbProperty("definition")
    private String definition;

    @JsonbProperty("description")
    private String description;

    /**
     * The fully qualified path to a file that will be created
     *
     */
    @JsonbProperty("path")
    private String path;

    public Output() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Output{" +
                "name='" + name + '\'' +
                ", applyTo=" + applyTo +
                ", definition='" + definition + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Output output = (Output) o;
        return Objects.equals(name, output.name) &&
                Objects.equals(applyTo, output.applyTo) &&
                Objects.equals(definition, output.definition) &&
                Objects.equals(description, output.description) &&
                Objects.equals(path, output.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, applyTo, definition, description, path);
    }
}
