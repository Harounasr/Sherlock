package de.ssherlock.persistence.exception;

public class NonExistentCheckerException extends NonExistentException {
    public NonExistentCheckerException(String message, Throwable err) {
        super(message, err);
    }
}
