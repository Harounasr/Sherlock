package de.ssherlock.persistence.exception;

public class NonExistentCourseException extends NonExistentException {
    public NonExistentCourseException(String message, Throwable err) {
        super(message, err);
    }
}
