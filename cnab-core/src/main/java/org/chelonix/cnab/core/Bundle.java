
package org.chelonix.cnab.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;

import org.leadpony.justify.api.JsonSchema;

/**
 * CNAB Bundle Descriptor
 * <p>
 * Cloud Native Application - Core version 1 - Bundle Descriptor
 *
 */
public class Bundle {

    /**
     * Custom actions that can be triggered on this bundle, action name should be namespaced
     * and use reverse DNS notation
     * 
     */
    @JsonbProperty("actions")
    private Map<String, Action> actions = new HashMap<>();

    /**
     * Credential to be injected into the invocation image
     * 
     */
    @JsonbProperty("credentials")
    private Map<String, Credential> credentials;

    @JsonbProperty("custom")
    private Map<String, Object> custom;

    @JsonbProperty("definitions")
    private Map<String, JsonSchema> definitions;

    /**
     * A description of this bundle, intended for users
     * 
     */
    @JsonbProperty("description")
    private String description;

    /**
     * Image that are used by this bundle
     * 
     */
    @JsonbProperty("image")
    private Image image;

    /**
     * The array of invocation image definitions for this bundle
     * (Required)
     * 
     */
    @JsonbProperty("invocationImages")
    private List<InvocationImage> invocationImages = null;

    /**
     * A list of keywords describing the bundle, intended for users
     * 
     */
    @JsonbProperty("keywords")
    private List<String> keywords = null;

    /**
     * The SPDX license code or proprietary license name for this bundle
     * 
     */
    @JsonbProperty("license")
    private String license;

    /**
     * A list of parties responsible for this bundle, with contact info
     * 
     */
    @JsonbProperty("maintainers")
    private List<Maintainer> maintainers = null;

    /**
     * The name of this bundle
     * (Required)
     * 
     */
    @JsonbProperty("name")
    private String name;

    /**
     * Values that are produced by executing the invocation image
     * 
     */
    @JsonbProperty("outputs")
    private Map<String, Output> outputs;

    /**
     * Parameters that can be injected into the invocation image
     * 
     */
    @JsonbProperty("parameters")
    private Map<String, Parameter> parameters = new HashMap<>();

    /**
     * A collection of extensions required for this bundle.
     * 
     */
    @JsonbProperty("requiredExtensions")
    private List<Object> requiredExtensions = null;

    /**
     * The version of the CNAB specification. This should always be the string 'v1' for this schema version.
     * (Required)
     * 
     */
    @JsonbProperty("schemaVersion")
    private String schemaVersion = SchemaVersion.V1_0_0;

    /**
     * A SemVer2 version for this bundle
     * (Required)
     * 
     */
    @JsonbProperty("version")
    private String version;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Bundle() {
    }

    /**
     * Custom actions that can be triggered on this bundle, action name should be namespaced and use reverse DNS notation
     * 
     */
    public Map<String, Action> getActions() {
        return actions;
    }

    /**
     * Custom actions that can be triggered on this bundle, action name should be namespaced and use reverse DNS notation
     * 
     */
    public void setActions(Map<String, Action> actions) {
        this.actions = actions;
    }

    public Bundle withActions(Map<String, Action> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * Credential to be injected into the invocation image
     * 
     */
    public Map<String, Credential> getCredentials() {
        return credentials;
    }

    /**
     * Credential to be injected into the invocation image
     * 
     */
    public void setCredentials(Map<String, Credential> credentials) {
        this.credentials = credentials;
    }

    public Bundle withCredentials(Map<String, Credential> credentials) {
        this.credentials = credentials;
        return this;
    }

    public Map<String, Object> getCustom() {
        return custom;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }

    public Bundle withCustom(Map<String, Object> custom) {
        this.custom = custom;
        return this;
    }

    public Map<String, JsonSchema> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, JsonSchema> definitions) {
        this.definitions = definitions;
    }

    public Bundle withDefinitions(Map<String, JsonSchema> definitions) {
        this.definitions = definitions;
        return this;
    }

    /**
     * A description of this bundle, intended for users
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * A description of this bundle, intended for users
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Bundle withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Image that are used by this bundle
     * 
     */
    public Image getImage() {
        return image;
    }

    /**
     * Image that are used by this bundle
     * 
     */
    public void setImage(Image image) {
        this.image = image;
    }

    public Bundle withImages(Image image) {
        this.image = image;
        return this;
    }

    /**
     * The array of invocation image definitions for this bundle
     * (Required)
     * 
     */
    public List<InvocationImage> getInvocationImages() {
        return invocationImages;
    }

    /**
     * The array of invocation image definitions for this bundle
     * (Required)
     * 
     */
    public void setInvocationImages(List<InvocationImage> invocationImages) {
        this.invocationImages = invocationImages;
    }

    public Bundle withInvocationImages(List<InvocationImage> invocationImages) {
        this.invocationImages = invocationImages;
        return this;
    }

    /**
     * A list of keywords describing the bundle, intended for users
     * 
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * A list of keywords describing the bundle, intended for users
     * 
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Bundle withKeywords(String... keyword) {
        if (keywords == null) {
            keywords = new ArrayList<>();
        }
        this.keywords.addAll(Arrays.asList(keyword));
        return this;
    }

    /**
     * The SPDX license code or proprietary license name for this bundle
     * 
     */
    public String getLicense() {
        return license;
    }

    /**
     * The SPDX license code or proprietary license name for this bundle
     * 
     */
    public void setLicense(String license) {
        this.license = license;
    }

    public Bundle withLicense(String license) {
        this.license = license;
        return this;
    }

    /**
     * A list of parties responsible for this bundle, with contact info
     * 
     */
    public List<Maintainer> getMaintainers() {
        return maintainers;
    }

    /**
     * A list of parties responsible for this bundle, with contact info
     * 
     */
    public void setMaintainers(List<Maintainer> maintainers) {
        this.maintainers = maintainers;
    }

    public Bundle withMaintainers(List<Maintainer> maintainers) {
        this.maintainers = maintainers;
        return this;
    }

    /**
     * The name of this bundle
     * (Required)
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * The name of this bundle
     * (Required)
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    public Bundle withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Values that are produced by executing the invocation image
     * 
     */
    public Map<String, Output> getOutputs() {
        return outputs;
    }

    /**
     * Values that are produced by executing the invocation image
     * 
     */
    public void setOutputs(Map<String, Output> outputs) {
        this.outputs = outputs;
    }

    public Bundle withOutput(String name, Output output) {
        if (this.outputs == null) {
            this.outputs = new HashMap<>();
        }
        this.outputs.put(name, output);
        return this;
    }

    /**
     * Parameters that can be injected into the invocation image
     * 
     */
    public Map<String, Parameter> getParameters() {
        return parameters;
    }

    /**
     * Parameters that can be injected into the invocation image
     * 
     */
    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    }

    public Bundle withParameter(String name, Parameter parameter) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }
        this.parameters.put(name, parameter);
        return this;
    }

    /**
     * A collection of extensions required for this bundle.
     * 
     */
    public List<Object> getRequiredExtensions() {
        return requiredExtensions;
    }

    /**
     * A collection of extensions required for this bundle.
     * 
     */
    public void setRequiredExtensions(List<Object> requiredExtensions) {
        this.requiredExtensions = requiredExtensions;
    }

    public Bundle withRequiredExtensions(List<Object> requiredExtensions) {
        this.requiredExtensions = requiredExtensions;
        return this;
    }

    /**
     * The version of the CNAB specification. This should always be the string 'v1' for this schema version.
     * (Required)
     * 
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * The version of the CNAB specification. This should always be the string 'v1' for this schema version.
     * (Required)
     * 
     */
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public Bundle withSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
        return this;
    }

    /**
     * A SemVer2 version for this bundle
     * (Required)
     * 
     */
    public String getVersion() {
        return version;
    }

    /**
     * A SemVer2 version for this bundle
     * (Required)
     * 
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public Bundle withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bundle.class.getSimpleName() + "[", "]")
                .add("actions=" + actions)
                .add("credentials=" + credentials)
                .add("custom=" + custom)
                .add("definitions=" + definitions)
                .add("description='" + description + "'")
                .add("image=" + image)
                .add("invocationImages=" + invocationImages)
                .add("keywords=" + keywords)
                .add("license='" + license + "'")
                .add("maintainers=" + maintainers)
                .add("name='" + name + "'")
                .add("outputs=" + outputs)
                .add("parameters=" + parameters)
                .add("requiredExtensions=" + requiredExtensions)
                .add("schemaVersion='" + schemaVersion + "'")
                .add("version='" + version + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bundle bundle = (Bundle) o;
        return Objects.equals(actions, bundle.actions) &&
                Objects.equals(credentials, bundle.credentials) &&
                Objects.equals(custom, bundle.custom) &&
                Objects.equals(definitions, bundle.definitions) &&
                Objects.equals(description, bundle.description) &&
                Objects.equals(image, bundle.image) &&
                Objects.equals(invocationImages, bundle.invocationImages) &&
                Objects.equals(keywords, bundle.keywords) &&
                Objects.equals(license, bundle.license) &&
                Objects.equals(maintainers, bundle.maintainers) &&
                name.equals(bundle.name) &&
                Objects.equals(outputs, bundle.outputs) &&
                Objects.equals(parameters, bundle.parameters) &&
                Objects.equals(requiredExtensions, bundle.requiredExtensions) &&
                schemaVersion.equals(bundle.schemaVersion) &&
                version.equals(bundle.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actions, credentials, custom, definitions, description, image, invocationImages, keywords, license, maintainers, name, outputs, parameters, requiredExtensions, schemaVersion, version);
    }
}
