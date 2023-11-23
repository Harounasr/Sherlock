package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an attempt to interact with a non-existent exercise in the database.
 *
 * @author Leon Höfling
 */
public class PersistenceNonExistentExerciseException extends Exception {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PersistenceNonExistentExerciseException.
     */
    public PersistenceNonExistentExerciseException() {
        super();
    }

    /**
     * Constructs a new PersistenceNonExistentExerciseException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public PersistenceNonExistentExerciseException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new PersistenceNonExistentExerciseException with the specified detail message and cause.
     *
     * @param msg  The detail message.
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public PersistenceNonExistentExerciseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
