package de.ssherlock.persistence.exception;

import java.io.Serial;

public class EmailAddressAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAddressAlreadyExistsException() {
        super();
    }

    public EmailAddressAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAddressAlreadyExistsException(String message, Throwable err) {
        super(message, err);
    }

}
