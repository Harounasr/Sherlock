package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessExerciseNameAlreadyExistsException() {
        super();
    }

    public BusinessExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    public BusinessExerciseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
