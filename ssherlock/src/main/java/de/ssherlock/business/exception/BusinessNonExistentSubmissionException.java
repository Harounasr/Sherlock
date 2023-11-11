package de.ssherlock.business.exception;

import java.io.Serial;

public class BusinessNonExistentSubmissionException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessNonExistentSubmissionException() {
        super();
    }

    public BusinessNonExistentSubmissionException(String msg) {
        super(msg);
    }

    public BusinessNonExistentSubmissionException(String msg, Throwable err) {
        super(msg, err);
    }
}
