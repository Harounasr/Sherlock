package de.ssherlock.business.exception;

import java.io.Serial;

public class B_DBAccessException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public B_DBAccessException() {
        super();
    }

    public B_DBAccessException(String message) {
        super(message);
    }

    public B_DBAccessException(String message, Throwable err) {
        super(message, err);
    }
}
