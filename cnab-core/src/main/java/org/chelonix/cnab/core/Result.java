package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.adapter.JsonbAdapter;

public class Result {

    public enum Status {
        FAILURE, SUCCESS, UNDERWAY, UNKNOWN
    }

    public static class StatusMapper implements JsonbAdapter<Status, String> {
        @Override
        public String adaptToJson(Status status) throws Exception {
            return status.name().toLowerCase();
        }

        @Override
        public Status adaptFromJson(String s) throws Exception {
            return Status.valueOf(s.toUpperCase());
        }
    }

    /**
     * The name of the action. One of the built-ins (install, uninstall, upgrade)
     * or a custom action.
     *
     */
    private String action;

    /**
     * the last message from the invocation image or runtime
     *
     */
    private String message;

    /**
     * The status of the last action
     *
     */
    private Status status = Status.UNKNOWN;

    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("action='" + action + "'")
                .add("message='" + message + "'")
                .add("status=" + status)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(action, result.action) &&
                Objects.equals(message, result.message) &&
                status == result.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, message, status);
    }
}
