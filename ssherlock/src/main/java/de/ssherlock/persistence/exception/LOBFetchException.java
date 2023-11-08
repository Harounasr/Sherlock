package de.ssherlock.persistence.exception;

import java.io.Serial;

public class LOBFetchException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public LOBFetchException() {
        super();
    }

    public LOBFetchException(String message) {
        super(message);
    }

    public LOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
