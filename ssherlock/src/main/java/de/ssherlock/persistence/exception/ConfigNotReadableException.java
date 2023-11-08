package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class ConfigNotReadableException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ConfigNotReadableException() {
        super();
    }

    public ConfigNotReadableException(String message) {
        super(message);
    }

    public ConfigNotReadableException(String message, Throwable err) {
        super(message, err);
    }
}
