package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a Course is not in the database.
 *
 * @author Leon Föckersperger
 */
public class BusinessNonExistentCourseException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BusinessNonExistentCourseException and sets the message.
     */
    public BusinessNonExistentCourseException() {
        super();
    }

    /**
     * Constructs a new BusinessNonExistentCourseException and sets the message.
     *
     * @param message stores the message.
     */
    public BusinessNonExistentCourseException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessNonExistentCourseException and sets the message and the error.
     *
     * @param message stores the message.
     * @param err     the exception which is going to be wrapped.
     */
    public BusinessNonExistentCourseException(String message, Throwable err) {
        super(message, err);
    }
}
