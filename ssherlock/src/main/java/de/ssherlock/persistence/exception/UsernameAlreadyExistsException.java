package de.ssherlock.persistence.exception;

import java.io.Serial;

public class UsernameAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException() {
        super();
    }

    public UsernameAlreadyExistsException(String message) {
        super();
    }

    public UsernameAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }
}
