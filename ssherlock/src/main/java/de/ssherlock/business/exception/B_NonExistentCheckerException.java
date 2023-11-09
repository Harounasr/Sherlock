package de.ssherlock.business.exception;

import java.io.Serial;

public class B_NonExistentCheckerException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public B_NonExistentCheckerException() {
        super();
    }

    public B_NonExistentCheckerException(String msg) {
        super(msg);
    }

    public B_NonExistentCheckerException(String msg, Throwable err) {
        super(msg, err);
    }
}
