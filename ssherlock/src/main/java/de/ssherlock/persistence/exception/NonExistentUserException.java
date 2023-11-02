package de.ssherlock.persistence.exception;

public class NonExistentUserException extends NonExistentException {
    public NonExistentUserException(String message, Throwable err) {
        super(message, err);
    }
}
