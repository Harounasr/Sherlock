package de.ssherlock.business.exception;

import java.io.Serial;

public class MailUnavailableException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public MailUnavailableException() {
        super();
    }

    public MailUnavailableException(String message) {
        super(message);
    }

    public MailUnavailableException(String message, Throwable err) {
        super(message, err);
    }
}
