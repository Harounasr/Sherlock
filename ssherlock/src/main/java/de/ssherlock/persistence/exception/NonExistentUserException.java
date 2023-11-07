package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class NonExistentUserException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public NonExistentUserException() {
        super();
    }

    public NonExistentUserException(String msg) {
        super(msg);
    }

    public NonExistentUserException(String msg, Throwable err) {
        super(msg, err);
    }
}