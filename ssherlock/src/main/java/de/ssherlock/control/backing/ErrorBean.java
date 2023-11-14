package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class ErrorBean {

    private final Logger logger;
    private final AppSession appSession;

    private Error error;

    @Inject
    public ErrorBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }



}
