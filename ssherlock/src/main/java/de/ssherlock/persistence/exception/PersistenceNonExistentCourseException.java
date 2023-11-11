package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceNonExistentCourseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenceNonExistentCourseException() {
        super();
    }

    public PersistenceNonExistentCourseException(String msg) {
        super(msg);
    }

    public PersistenceNonExistentCourseException(String msg, Throwable err) {
        super(msg, err);
    }
}
