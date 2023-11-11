package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceCourseNameAlreadyExistsException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;
    public PersistenceCourseNameAlreadyExistsException() {
        super();
    }

    public PersistenceCourseNameAlreadyExistsException(String message) {
        super(message);
    }

    public PersistenceCourseNameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
