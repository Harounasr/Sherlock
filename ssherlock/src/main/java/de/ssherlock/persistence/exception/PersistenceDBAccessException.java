package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an access issue to the database.
 *
 * @author Victor Vollmann
 */
public class PersistenceDBAccessException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceDBAccessException.
     */
    public PersistenceDBAccessException() {
        super();
    }

    /**
     * Constructs a new PersistenceDBAccessException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceDBAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceDBAccessException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceDBAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}


