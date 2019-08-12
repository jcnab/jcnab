
package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;

import javax.json.bind.annotation.JsonbProperty;

/**
 * Custom actions that can be triggered on this bundle, action name should be namespaced and use reverse DNS notation
 * 
 */
public class Action {

    /**
     * A description of the purpose of this action
     */
    @JsonbProperty("description")
    private String description;

    /**
     * Must be set to true if the action can change any resource managed by this bundle
     */
    @JsonbProperty("modifies")
    private boolean modifies;

    /**
     * Indicates that the action is purely informational, that credentials are not required, and that the runtime
     * should not keep track of its invocation
     */
    @JsonbProperty("stateless")
    private boolean stateless = false;

    public Action() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Action.class.getSimpleName() + "[", "]")
                .toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isModifies() {
        return modifies;
    }

    public void setModifies(boolean modifies) {
        this.modifies = modifies;
    }

    public boolean isStateless() {
        return stateless;
    }

    public void setStateless(boolean stateless) {
        this.stateless = stateless;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return modifies == action.modifies &&
                stateless == action.stateless &&
                Objects.equals(description, action.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, modifies, stateless);
    }
}
