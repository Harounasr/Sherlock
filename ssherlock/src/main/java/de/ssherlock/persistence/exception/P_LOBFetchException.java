package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_LOBFetchException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public P_LOBFetchException() {
        super();
    }

    public P_LOBFetchException(String message) {
        super(message);
    }

    public P_LOBFetchException(String message, Throwable err) {
        super(message, err);
    }
}
