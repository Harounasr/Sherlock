package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * Runtime exception indicating that the configuration file is not readable.
 *
 * @author Leon Höfling
 */
public class ConfigNotReadableException extends RuntimeException {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConfigNotReadableException.
     */
    public ConfigNotReadableException() {
        super();
    }

    /**
     * Constructs a new ConfigNotReadableException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ConfigNotReadableException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConfigNotReadableException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public ConfigNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }
}

