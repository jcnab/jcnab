
package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

/**
 * Credential defines a particular credential, and where it should be placed in the invocation image
 * 
 */
public class Credential implements MapOf.Named {

    @JsonbTransient
    private String name;

    /**
     * A user-friendly description of this credential
     */
    @JsonbProperty("description")
    private String description;

    /**
     * The environment variable name, such as MY_VALUE, into which the credential will be placed
     */
    @JsonbProperty("env")
    private String env;

    /**
     * The path inside of the invocation image where credentials will be mounted
     */
    @JsonbProperty("path")
    private String path;

    /**
     * Indicates whether this credential must be supplied. By default, credentials are optional
     */
    @JsonbProperty("required")
    private boolean required = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
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
        return "Credential{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", env='" + env + '\'' +
                ", path='" + path + '\'' +
                ", required=" + required +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return required == that.required &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(env, that.env) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, env, path, required);
    }
}
