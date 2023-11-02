package de.ssherlock.persistence.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message, Throwable err) {
        super(message, err);
    }
}
