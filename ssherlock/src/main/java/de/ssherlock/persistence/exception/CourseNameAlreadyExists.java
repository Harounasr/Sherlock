package de.ssherlock.persistence.exception;

import java.io.Serial;

public class CourseNameAlreadyExists extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;
    public CourseNameAlreadyExists() {
        super();
    }

    public CourseNameAlreadyExists(String message) {
        super(message);
    }

    public CourseNameAlreadyExists(String message, Throwable err) {
        super(message, err);
    }
}
