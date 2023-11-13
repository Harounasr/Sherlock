package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessCourseNameAlreadyExistsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessCourseNameAlreadyExistsException() {
        super();
    }

    public BusinessCourseNameAlreadyExistsException(String message) {
        super(message);
    }

    public BusinessCourseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
