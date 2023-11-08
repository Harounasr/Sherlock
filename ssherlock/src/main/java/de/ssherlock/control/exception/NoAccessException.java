package de.ssherlock.control.exception;

import java.io.Serial;

public class NoAccessException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;
    public NoAccessException() {
        super();
    }

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException(String message, Throwable err) {
        super(message, err);
    }
}
