package de.ssherlock.business.exception;

public class RegistrationFailedException extends Exception {

    public RegistrationFailedException() {
        super();
    }

    public RegistrationFailedException(String msg) {
        super(msg);
    }

    public RegistrationFailedException(String msg, Throwable err) {
        super(msg, err);
    }

}
