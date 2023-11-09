package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_NonExistentCourseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public P_NonExistentCourseException() {
        super();
    }

    public P_NonExistentCourseException(String msg) {
        super(msg);
    }

    public P_NonExistentCourseException(String msg, Throwable err) {
        super(msg, err);
    }
}
