package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an attempt to interact with a non-existent course in the database.
 *
 * @author Victor Vollmann
 */
public class PersistenceNonExistentCourseException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentCourseException.
     */
    public PersistenceNonExistentCourseException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentCourseException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceNonExistentCourseException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceNonExistentCourseException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceNonExistentCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
