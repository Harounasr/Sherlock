package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when the registration did not work.
 */
public class RegistrationFailedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a new RegistrationFailedException and sets the message.
     */
    public RegistrationFailedException() {
        super();
    }
    /**
     * Constructs a new RegistrationFailedException and sets the message.
     *
     * @param message stores the message.
     */
    public RegistrationFailedException(String message) {
        super(message);
    }
    /**
     * Constructs a new RegistrationFailedException and sets the message.
     *
     * @param message stores the message.
     * @param err the exception which is going to be wrapped.
     */
    public RegistrationFailedException(String message, Throwable err) {
        super(message, err);
    }

}
