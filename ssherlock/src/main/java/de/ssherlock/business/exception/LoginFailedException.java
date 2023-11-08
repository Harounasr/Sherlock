package de.ssherlock.business.exception;

public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String msg) {
        super(msg);
    }

    public LoginFailedException(String msg, Throwable err) {
        super(msg, err);
    }

}
