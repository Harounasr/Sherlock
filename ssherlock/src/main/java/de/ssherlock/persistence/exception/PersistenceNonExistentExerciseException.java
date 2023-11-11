package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceNonExistentExerciseException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenceNonExistentExerciseException() {
        super();
    }

    public PersistenceNonExistentExerciseException(String msg) {
        super(msg);
    }

    public PersistenceNonExistentExerciseException(String msg, Throwable err) {
        super(msg, err);
    }
}
