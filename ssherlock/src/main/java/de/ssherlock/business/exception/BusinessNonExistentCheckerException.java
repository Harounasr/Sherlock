package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessNonExistentCheckerException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessNonExistentCheckerException() {
        super();
    }

    public BusinessNonExistentCheckerException(String msg) {
        super(msg);
    }

    public BusinessNonExistentCheckerException(String msg, Throwable err) {
        super(msg, err);
    }
}
