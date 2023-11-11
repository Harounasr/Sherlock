package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessDBAccessException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessDBAccessException() {
        super();
    }

    public BusinessDBAccessException(String message) {
        super(message);
    }

    public BusinessDBAccessException(String message, Throwable err) {
        super(message, err);
    }
}
