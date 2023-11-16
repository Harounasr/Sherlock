package de.ssherlock.control.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that a user doesn't have permission for the page.
 * This is a runtime exception, allowing it to be unchecked.
 */
public class NoAccessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new NoAccessException with no specific message.
     */
    public NoAccessException() {
        super();
    }

    /**
     * Constructs a new NoAccessException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public NoAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoAccessException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public NoAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
