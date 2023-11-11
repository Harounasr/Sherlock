package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;

public class Error implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Exception exception;

    private String message;

    private String stacktrace;

    public Error() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getStacktrace() {
        return stacktrace;
    }


}
