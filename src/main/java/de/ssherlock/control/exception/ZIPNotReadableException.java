package de.ssherlock.control.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that a ZIP file is not readable. This is a checked exception.
 *
 * @author Leon Föckersperger
 */
public class ZIPNotReadableException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ZIPNotReadableException with no specific message.
     */
    public ZIPNotReadableException() {
        super();
    }

    /**
     * Constructs a new ZIPNotReadableException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     */
    public ZIPNotReadableException(String message) {
        super(message);
    }

    /**
     * Constructs a new ZIPNotReadableException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()}
     *                method).
     */
    public ZIPNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }

}
