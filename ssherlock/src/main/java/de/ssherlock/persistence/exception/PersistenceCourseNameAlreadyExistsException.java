package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that a course name already exists in the database.
 */
public class PersistenceCourseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceCourseNameAlreadyExistsException.
     */
    public PersistenceCourseNameAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new PersistenceCourseNameAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceCourseNameAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceCourseNameAlreadyExistsException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceCourseNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
