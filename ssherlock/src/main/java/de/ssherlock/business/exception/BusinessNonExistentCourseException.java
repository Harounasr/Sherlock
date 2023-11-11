package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessNonExistentCourseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessNonExistentCourseException() {
        super();
    }

    public BusinessNonExistentCourseException(String msg) {
        super(msg);
    }

    public BusinessNonExistentCourseException(String msg, Throwable err) {
        super(msg, err);
    }
}
