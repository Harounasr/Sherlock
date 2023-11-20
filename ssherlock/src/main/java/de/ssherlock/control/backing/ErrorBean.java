package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Error;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Backing bean for the error.xhtml facelet.
 */
@Named
@RequestScoped
public class ErrorBean {

    /**
     * Error instance to be handled.
     */
    private Error error;

    /**
     * Constructs an ErrorBean.
     *
     * @param appSession The active session (Injected).
     */
    public ErrorBean() {
        error = new Error();
        error.setMessage("");
    }

    /**
     * Sets the error instance.
     *
     * @param error The error instance to be set.
     */
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * Retrieves the error instance.
     *
     * @return The error instance.
     */
    public Error getError() {
        return error;
    }

}
