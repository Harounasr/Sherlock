package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_NonExistentCheckerException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;
    public P_NonExistentCheckerException() {
        super();
    }

    public P_NonExistentCheckerException(String msg) {
        super(msg);
    }

    public P_NonExistentCheckerException(String msg, Throwable err) {
        super(msg, err);
    }
}
