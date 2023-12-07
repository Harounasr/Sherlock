package de.ssherlock.control.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that a checker encountered an error
 * during its execution.
 *
 * @author Victor Vollmann
 */
public class CheckerExecutionException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new CheckerExecutionException with no specific message.
     */
    public CheckerExecutionException() {
        super();
    }

    /**
     * Constructs a new CheckerExecutionException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     */
    public CheckerExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs a new CheckerExecutionException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public CheckerExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
