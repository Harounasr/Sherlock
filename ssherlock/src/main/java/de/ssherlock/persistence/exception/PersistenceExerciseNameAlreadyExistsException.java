package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenceExerciseNameAlreadyExistsException() {
        super();
    }

    public PersistenceExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    public PersistenceExerciseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
