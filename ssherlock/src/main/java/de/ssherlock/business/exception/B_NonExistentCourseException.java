package de.ssherlock.business.exception;

import java.io.Serial;

public class B_NonExistentCourseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public B_NonExistentCourseException() {
        super();
    }

    public B_NonExistentCourseException(String msg) {
        super(msg);
    }

    public B_NonExistentCourseException(String msg, Throwable err) {
        super(msg, err);
    }
}
