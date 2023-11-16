package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating that an exercise name already exists in the database.
 */
public class PersistenceExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceExerciseNameAlreadyExistsException.
     */
    public PersistenceExerciseNameAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new PersistenceExerciseNameAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PersistenceExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceExerciseNameAlreadyExistsException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public PersistenceExerciseNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
