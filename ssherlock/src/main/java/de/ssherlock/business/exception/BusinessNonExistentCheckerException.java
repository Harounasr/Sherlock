package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a checker is not in the database.
 *
 * @author Victor Vollmann
 */
public class BusinessNonExistentCheckerException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BusinessNonExistentCheckerException and sets the message.
     */
    public BusinessNonExistentCheckerException() {
        super();
    }

    /**
     * Constructs a new BusinessNonExistentCheckerException and sets the message.
     *
     * @param message stores the message.
     */
    public BusinessNonExistentCheckerException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessNonExistentCheckerException and sets the message and the error.
     *
     * @param message stores the message.
     * @param err the exception which is going to be wrapped.
     */
    public BusinessNonExistentCheckerException(String message, Throwable err) {
        super(message, err);
    }
}
