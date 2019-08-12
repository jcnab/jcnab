
package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;

/**
 * An object that describes a maintainer
 * 
 */
public class Maintainer {

    /**
     * Email address of responsible party
     * 
     */
    @JsonbProperty("email")
    private String email;
    /**
     * Name of party reponsible for this bundle
     * (Required)
     * 
     */
    @JsonbProperty("name")
    private String name;
    /**
     * URL of the responsible party, perhaps containing additional contact info
     * 
     */
    @JsonbProperty("url")
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Maintainer() {
    }

    /**
     * 
     * @param email
     * @param name
     * @param url
     */
    public Maintainer(String email, String name, String url) {
        super();
        this.email = email;
        this.name = name;
        this.url = url;
    }

    /**
     * Email address of responsible party
     * 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email address of responsible party
     * 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public Maintainer withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Name of party reponsible for this bundle
     * (Required)
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Name of party reponsible for this bundle
     * (Required)
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    public Maintainer withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * URL of the responsible party, perhaps containing additional contact info
     * 
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL of the responsible party, perhaps containing additional contact info
     * 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Maintainer withUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Maintainer.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("name='" + name + "'")
                .add("url='" + url + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maintainer that = (Maintainer) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, url);
    }
}
