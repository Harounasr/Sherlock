package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Is thrown when the sending of mail did not work.
 *
 * @author Leon Höfling
 */
public class MailUnavailableException extends RuntimeException {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new MailUnavailableException and sets the message.
     */
    public MailUnavailableException() {
        super();
    }

    /**
     * Constructs a new MailUnavailableException and sets the message.
     *
     * @param message stores the message.
     */
    public MailUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructs a new MailUnavailableException and sets the message.
     *
     * @param message stores the message.
     * @param err     the exception which is going to be wrapped.
     */
    public MailUnavailableException(String message, Throwable err) {
        super(message, err);
    }
}
