package de.ssherlock.control.exception;

import java.io.Serial;

public class HashAlgoUnavailableException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public HashAlgoUnavailableException() {
        super();
    }

    public HashAlgoUnavailableException(String message) {
        super(message);
    }

    public HashAlgoUnavailableException(String message, Throwable err) {
        super(message, err);
    }
}
