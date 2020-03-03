package org.chelonix.cnab.driver;

/**
 * This exception is thrown when a driver operation is not valid according to the bundle definition.
 * <p/>
 * It covers:
 * <ul>
 *     <li>unsupported action</li>
 *     <li>bad parameter or credentials type</li>
 *     <li>...</li>
 * </ul>
 */
public class InvalidOperationException extends DriverException {

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Object... args) {
        super(String.format(message, args));
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }

    public InvalidOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
