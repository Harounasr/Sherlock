package de.ssherlock.persistence.exception;

public class NonExistentException extends RuntimeException {
    public NonExistentException(String message, Throwable err) {
        super(message, err);
    }
}
