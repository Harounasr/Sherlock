package de.ssherlock.persistence.exception;

public class DatabseConnectionFailedException extends RuntimeException {
    public DatabseConnectionFailedException(String message, Throwable err) {
        super(message, err);
    }
}
