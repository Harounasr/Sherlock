package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class DatabaseConnectionFailedException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public DatabaseConnectionFailedException() {
        super();
    }
    public DatabaseConnectionFailedException(String msg) {
        super(msg);
    }
    public DatabaseConnectionFailedException(String msg, Throwable err) {
        super(msg, err);
    }
}
