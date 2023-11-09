package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_ExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public P_ExerciseNameAlreadyExistsException() {
        super();
    }

    public P_ExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    public P_ExerciseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
