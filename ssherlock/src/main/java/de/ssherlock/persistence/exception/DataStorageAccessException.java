package de.ssherlock.persistence.exception;

import java.io.Serial;

public class DataStorageAccessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    public DataStorageAccessException() {
        super();
    }

    public DataStorageAccessException(String message) {
        super(message);
    }

    public DataStorageAccessException(String message, Throwable err) {
        super(message, err);
    }
}
