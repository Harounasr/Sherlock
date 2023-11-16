package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that a user does not exist in the database.
 */
public class NonExistentUserException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new NonExistentUserException.
     */
    public NonExistentUserException() {
        super();
    }

    /**
     * Constructs a new NonExistentUserException with the specified detail message.
     *
     * @param message The detail message.
     */
    public NonExistentUserException(String message) {
        super(message);
    }

    /**
     * Constructs a new NonExistentUserException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public NonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
