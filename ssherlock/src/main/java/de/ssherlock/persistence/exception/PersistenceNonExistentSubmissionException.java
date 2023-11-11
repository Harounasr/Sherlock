package de.ssherlock.persistence.exception;

import java.io.Serial;

public class PersistenceNonExistentSubmissionException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenceNonExistentSubmissionException() {
        super();
    }

    public PersistenceNonExistentSubmissionException(String msg) {
        super(msg);
    }

    public PersistenceNonExistentSubmissionException(String msg, Throwable err) {
        super(msg, err);
    }
}

