package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that an attempt to register a user with an existing username was made.
 *
 * @author Victor Vollmann
 */
public class UsernameAlreadyExistsException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UsernameAlreadyExistsException.
     */
    public UsernameAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new UsernameAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new UsernameAlreadyExistsException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
