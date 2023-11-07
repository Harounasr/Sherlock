package de.ssherlock.persistence.exception;

import java.io.Serializable;

public class NonExistentExerciseException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public NonExistentExerciseException() {
        super();
    }

    public NonExistentExerciseException(String msg) {
        super(msg);
    }

    public NonExistentExerciseException(String msg, Throwable err) {
        super(msg, err);
    }
}
