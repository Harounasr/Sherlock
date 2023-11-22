package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * Runtime exception indicating that the database is unavailable.
 */
public class DBUnavailableException extends RuntimeException {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DBUnavailableException.
     */
    public DBUnavailableException() {
        super();
    }

    /**
     * Constructs a new DBUnavailableException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DBUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructs a new DBUnavailableException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public DBUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
