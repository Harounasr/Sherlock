package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessNonExistentExerciseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessNonExistentExerciseException() {
        super();
    }

    public BusinessNonExistentExerciseException(String msg) {
        super(msg);
    }

    public BusinessNonExistentExerciseException(String msg, Throwable err) {
        super(msg, err);
    }
}
