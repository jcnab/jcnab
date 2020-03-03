package org.chelonix.cnab.core;

import java.io.IOException;

public class BundleIOException extends IOException {

    public BundleIOException(String message) {
        super(message);
    }

    public BundleIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public BundleIOException(Throwable cause) {
        super(cause);
    }
}
