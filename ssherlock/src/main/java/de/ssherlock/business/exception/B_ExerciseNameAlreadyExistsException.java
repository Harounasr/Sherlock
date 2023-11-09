package de.ssherlock.business.exception;

import java.io.Serial;

public class B_ExerciseNameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public B_ExerciseNameAlreadyExistsException() {
        super();
    }

    public B_ExerciseNameAlreadyExistsException(String message) {
        super(message);
    }

    public B_ExerciseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
