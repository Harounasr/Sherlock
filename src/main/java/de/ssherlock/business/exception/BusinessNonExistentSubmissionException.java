package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a Submission is not in the database.
 *
 * @author Victor Vollmann
 */
public class BusinessNonExistentSubmissionException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BusinessNonExistentSubmissionException and sets the message.
     */
    public BusinessNonExistentSubmissionException() {
        super();
    }

    /**
     * Constructs a new BusinessNonExistentSubmissionException and sets the message.
     *
     * @param message stores the message.
     */
    public BusinessNonExistentSubmissionException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessNonExistentSubmissionException and sets the message and the error.
     *
     * @param message stores the message.
     * @param err     the exception which is going to be wrapped.
     */
    public BusinessNonExistentSubmissionException(String message, Throwable err) {
        super(message, err);
    }
}
