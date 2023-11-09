package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class DBUnavailableException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public DBUnavailableException() {
        super();
    }
    public DBUnavailableException(String msg) {
        super(msg);
    }
    public DBUnavailableException(String msg, Throwable err) {
        super(msg, err);
    }
}
