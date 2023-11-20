package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessNonExistentUserException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BusinessNonExistentUserException.
     */
    public BusinessNonExistentUserException() {
        super();
    }

    /**
     * Constructs a new BusinessNonExistentUserException with the specified detail message.
     *
     * @param message The detail message.
     */
    public BusinessNonExistentUserException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessNonExistentUserException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public BusinessNonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
