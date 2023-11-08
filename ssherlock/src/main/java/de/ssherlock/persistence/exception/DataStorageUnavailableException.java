package de.ssherlock.persistence.exception;

import java.io.Serial;
import java.io.Serializable;

public class DataStorageUnavailableException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public DataStorageUnavailableException() {
        super();
    }
    public DataStorageUnavailableException(String msg) {
        super(msg);
    }
    public DataStorageUnavailableException(String msg, Throwable err) {
        super(msg, err);
    }
}
