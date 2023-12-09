package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when the Database access is not working.
 *
 * @author Leon Föckersperger
 */
public class BusinessDBAccessException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BusinessDBAccessException.
     */
    public BusinessDBAccessException() {
        super();
    }

    /**
     * Constructs a new BusinessDBAccessException and sets the message.
     *
     * @param message stores the message.
     */
    public BusinessDBAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessDBAccessException and sets the message.
     *
     * @param message stores the message.
     * @param err     the exception which is going to be wrapped.
     */
    public BusinessDBAccessException(String message, Throwable err) {
        super(message, err);
    }
}
