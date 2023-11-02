package de.ssherlock.persistence.exception;

public class DuplicateUsernameException extends DuplicateException {
    public DuplicateUsernameException(String message, Throwable err) {
        super(message, err);
    }
}
