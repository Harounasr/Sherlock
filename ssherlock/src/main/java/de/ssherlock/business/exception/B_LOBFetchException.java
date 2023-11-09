package de.ssherlock.business.exception;

import java.io.Serial;

public class B_LOBFetchException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public B_LOBFetchException() {
        super();
    }

    public B_LOBFetchException(String message) {
        super(message);
    }

    public B_LOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
