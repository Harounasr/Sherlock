package de.ssherlock.business.exception;

import java.io.Serial;

public class B_NonExistentSubmissionException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public B_NonExistentSubmissionException() {
        super();
    }

    public B_NonExistentSubmissionException(String msg) {
        super(msg);
    }

    public B_NonExistentSubmissionException(String msg, Throwable err) {
        super(msg, err);
    }
}
