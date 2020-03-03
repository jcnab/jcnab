package org.chelonix.cnab.core;

import de.huxhorn.sulky.ulid.ULID;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.json.bind.annotation.JsonbProperty;

/**
 * CNAB Claims json schema
 *
 */
public class Claim {

    /**
     * The bundle descriptor
     *
     */
    @JsonbProperty("bundle")
    private Bundle bundle;

    /**
     * The date created
     */
    @JsonbProperty("created")
    private LocalDateTime created;

    /**
     * The date last modified
     */
    @JsonbProperty("modified")
    private LocalDateTime modified;

    /**
     * The name of the installation
     */
    @JsonbProperty("name")
    private String name;

    /**
     * The result of the last action
     */
    @JsonbProperty("result")
    private Result result;

    /**
     * Key/value pairs that were passed in during the operation.
     */
    @JsonbProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

    @JsonbProperty("outputs")
    private Map<String, String> outputs;

    /**
     * the revision ID (ideally a ULID)
     */
    @JsonbProperty("revision")
    private String revision = new ULID().nextULID();

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Claim withParameter(String name, String value) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }
        this.parameters.put(name, value);
        return this;
    }

    public Map<String, String> getOutputs() {
        return outputs;
    }

    public void setOutputs(Map<String, String> outputs) {
        this.outputs = outputs;
    }

    public Claim withOutput(String key, String value) {
        if (outputs == null) {
            outputs = new HashMap<>();
        }
        outputs.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "bundle=" + bundle +
                ", created=" + created +
                ", modified=" + modified +
                ", name='" + name + '\'' +
                ", result=" + result +
                ", parameters=" + parameters +
                ", outputs=" + outputs +
                ", revision='" + revision + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim claim = (Claim) o;
        return Objects.equals(bundle, claim.bundle) &&
                Objects.equals(created, claim.created) &&
                Objects.equals(modified, claim.modified) &&
                Objects.equals(name, claim.name) &&
                Objects.equals(result, claim.result) &&
                Objects.equals(parameters, claim.parameters) &&
                Objects.equals(outputs, claim.outputs) &&
                Objects.equals(revision, claim.revision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bundle, created, modified, name, result, parameters, outputs, revision);
    }
}
