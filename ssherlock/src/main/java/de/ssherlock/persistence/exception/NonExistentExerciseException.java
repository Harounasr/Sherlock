package de.ssherlock.persistence.exception;

public class NonExistentExerciseException extends NonExistentException {
    public NonExistentExerciseException(String message, Throwable err) {
        super(message, err);
    }
}
