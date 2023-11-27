package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that an email address already exists in the database.
 *
 * @author Leon Höfling
 */
public class EmailAddressAlreadyExistsException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EmailAddressAlreadyExistsException.
     */
    public EmailAddressAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new EmailAddressAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public EmailAddressAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmailAddressAlreadyExistsException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public EmailAddressAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

