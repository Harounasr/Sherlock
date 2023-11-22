package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that a user does not exist in the database.
 */
public class PersistenceNonExistentUserException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentUserException.
     */
    public PersistenceNonExistentUserException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentUserException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceNonExistentUserException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceNonExistentUserException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceNonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
