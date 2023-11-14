package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class ErrorBean {

    private Error error;

    @Inject
    private Logger logger;

    @Inject
    private AppSession appSession;

    public ErrorBean() {
    }
    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }



}
