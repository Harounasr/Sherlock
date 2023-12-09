package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an attempt to interact with a non-existent checker in the database.
 *
 * @author Leon Föckersperger
 */
public class PersistenceNonExistentCheckerException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentCheckerException.
     */
    public PersistenceNonExistentCheckerException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentCheckerException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceNonExistentCheckerException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceNonExistentCheckerException with the specified detail message and
     * cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceNonExistentCheckerException(String message, Throwable cause) {
        super(message, cause);
    }
}
