package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceNonExistentCheckerException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;
    public PersistenceNonExistentCheckerException() {
        super();
    }

    public PersistenceNonExistentCheckerException(String msg) {
        super(msg);
    }

    public PersistenceNonExistentCheckerException(String msg, Throwable err) {
        super(msg, err);
    }
}
