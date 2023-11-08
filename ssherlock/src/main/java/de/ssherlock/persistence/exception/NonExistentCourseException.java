package de.ssherlock.persistence.exception;

import java.io.Serializable;

public class NonExistentCourseException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public NonExistentCourseException() {
        super();
    }

    public NonExistentCourseException(String msg) {
        super(msg);
    }

    public NonExistentCourseException(String msg, Throwable err) {
        super(msg, err);
    }
}
