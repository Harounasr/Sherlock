package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a user does not exist in the database.
 */
public class BusinessNonExistentUserException extends Exception {

    /**
     * Serial Version UID.
     */
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
     * @param err     the exception which is going to be wrapped.
     */
    public BusinessNonExistentUserException(String message, Throwable err) {
        super(message, err);
    }
}
