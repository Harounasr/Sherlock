package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Is thrown when a testate does not exist in the database.
 */
public class PersistenceNonExistentTestateException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentTestateException and sets the message.
     */
    public PersistenceNonExistentTestateException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentTestateException and sets the message.
     *
     * @param message stores the message.
     */
    public PersistenceNonExistentTestateException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceNonExistentTestateException and sets the message and the error.
     *
     * @param message stores the message.
     * @param err the exception which is going to be wrapped.
     */
    public PersistenceNonExistentTestateException(String message, Throwable err) {
        super(message, err);
    }

}
