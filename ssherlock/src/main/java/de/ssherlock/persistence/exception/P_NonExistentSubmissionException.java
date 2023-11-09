package de.ssherlock.persistence.exception;

import java.io.Serial;

public class P_NonExistentSubmissionException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public P_NonExistentSubmissionException() {
        super();
    }

    public P_NonExistentSubmissionException(String msg) {
        super(msg);
    }

    public P_NonExistentSubmissionException(String msg, Throwable err) {
        super(msg, err);
    }
}
