package de.ssherlock.persistence.exception;

import java.io.Serial;

public class ExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExerciseNameAlreadyExistsException() {
        super();
    }

    public ExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    public ExerciseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
