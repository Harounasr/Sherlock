package de.ssherlock.persistence.exception;

public class DuplicateEmailException extends DuplicateException {
    public DuplicateEmailException(String message, Throwable err) {
        super(message, err);
    }
}
