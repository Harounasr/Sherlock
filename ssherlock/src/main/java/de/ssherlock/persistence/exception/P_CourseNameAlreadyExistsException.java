package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_CourseNameAlreadyExistsException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;
    public P_CourseNameAlreadyExistsException() {
        super();
    }

    public P_CourseNameAlreadyExistsException(String message) {
        super(message);
    }

    public P_CourseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
