package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating a failure during Large Object fetch in the database.
 *
 * @author Victor Vollmann
 */
public class PersistenceLOBFetchException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceLOBFetchException.
     */
    public PersistenceLOBFetchException() {
        super();
    }

    /**
     * Constructs a new PersistenceLOBFetchException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceLOBFetchException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceLOBFetchException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceLOBFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
