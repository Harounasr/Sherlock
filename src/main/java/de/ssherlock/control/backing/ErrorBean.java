package de.ssherlock.control.backing;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Error;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Backing bean for the error.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class ErrorBean {

    /**
     * Error instance to be handled.
     */
    private Error error;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger = LoggerCreator.get(ErrorBean.class);

    /**
     * Constructs an ErrorBean.
     */
    public ErrorBean() {

    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    public void init() {
        logger.info("initialized");
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
