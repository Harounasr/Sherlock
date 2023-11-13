package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessLOBFetchException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessLOBFetchException() {
        super();
    }

    public BusinessLOBFetchException(String message) {
        super(message);
    }

    public BusinessLOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
