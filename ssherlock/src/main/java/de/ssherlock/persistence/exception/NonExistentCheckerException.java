package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class NonExistentCheckerException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;
    public NonExistentCheckerException() {
        super();
    }

    public NonExistentCheckerException(String msg) {
        super(msg);
    }

    public NonExistentCheckerException(String msg, Throwable err) {
        super(msg, err);
    }
}
