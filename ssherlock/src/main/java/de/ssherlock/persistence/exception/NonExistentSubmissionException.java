package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class NonExistentSubmissionException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public NonExistentSubmissionException() {
        super();
    }

    public NonExistentSubmissionException(String msg) {
        super(msg);
    }

    public NonExistentSubmissionException(String msg, Throwable err) {
        super(msg, err);
    }
}
