package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an attempt to interact with a non-existent submission in the database.
 */
public class PersistenceNonExistentSubmissionException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentSubmissionException.
     */
    public PersistenceNonExistentSubmissionException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentSubmissionException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public PersistenceNonExistentSubmissionException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new PersistenceNonExistentSubmissionException with the specified detail message and cause.
     *
     * @param msg  The detail message.
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public PersistenceNonExistentSubmissionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
