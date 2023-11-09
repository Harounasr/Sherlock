package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_NonExistentExerciseException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public P_NonExistentExerciseException() {
        super();
    }

    public P_NonExistentExerciseException(String msg) {
        super(msg);
    }

    public P_NonExistentExerciseException(String msg, Throwable err) {
        super(msg, err);
    }
}
