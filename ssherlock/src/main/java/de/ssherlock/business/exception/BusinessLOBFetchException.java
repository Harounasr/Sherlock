package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when .
 */
public class BusinessLOBFetchException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a new BusinessLOBFetchException and sets the message.
     */
    public BusinessLOBFetchException() {
        super();
    }
    /**
     * Constructs a new BusinessLOBFetchException and sets the message.
     *
     * @param message stores the message.
     */
    public BusinessLOBFetchException(String message) {
        super(message);
    }
    /**
     * Constructs a new BusinessLOBFetchException and sets the message.
     *
     * @param message stores the message.
     * @param err the exception which is going to be wrapped.
     */
    public BusinessLOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
