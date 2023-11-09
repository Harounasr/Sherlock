package de.ssherlock.business.exception;

import java.io.Serial;

public class B_CourseNameAlreadyExistsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public B_CourseNameAlreadyExistsException() {
        super();
    }

    public B_CourseNameAlreadyExistsException(String message) {
        super(message);
    }

    public B_CourseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
