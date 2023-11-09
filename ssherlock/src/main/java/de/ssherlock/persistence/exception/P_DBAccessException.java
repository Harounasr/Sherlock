package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_DBAccessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    public P_DBAccessException() {
        super();
    }

    public P_DBAccessException(String message) {
        super(message);
    }

    public P_DBAccessException(String message, Throwable err) {
        super(message, err);
    }
}
