package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceLOBFetchException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenceLOBFetchException() {
        super();
    }

    public PersistenceLOBFetchException(String message) {
        super(message);
    }

    public PersistenceLOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
