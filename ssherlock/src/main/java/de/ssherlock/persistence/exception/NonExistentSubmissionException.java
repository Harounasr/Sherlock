package de.ssherlock.persistence.exception;

public class NonExistentSubmissionException extends NonExistentException {
    public NonExistentSubmissionException(String message, Throwable err) {
        super(message, err);
    }
}
