package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceDBAccessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    public PersistenceDBAccessException() {
        super();
    }

    public PersistenceDBAccessException(String message) {
        super(message);
    }

    public PersistenceDBAccessException(String message, Throwable err) {
        super(message, err);
    }
}

