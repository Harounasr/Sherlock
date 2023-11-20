package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

/**
 * Backing bean for the error.xhtml facelet.
 */
@Named
@RequestScoped
public class ErrorBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Error instance to be handled.
     */
    private Error error;

    /**
     * Constructs an ErrorBean.
     *
     * @param logger     The logger used for logging within this class (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public ErrorBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
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
