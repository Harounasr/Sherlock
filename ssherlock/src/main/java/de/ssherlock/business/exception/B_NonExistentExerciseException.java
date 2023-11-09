package de.ssherlock.business.exception;

import java.io.Serial;

public class B_NonExistentExerciseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public B_NonExistentExerciseException() {
        super();
    }

    public B_NonExistentExerciseException(String msg) {
        super(msg);
    }

    public B_NonExistentExerciseException(String msg, Throwable err) {
        super(msg, err);
    }
}
