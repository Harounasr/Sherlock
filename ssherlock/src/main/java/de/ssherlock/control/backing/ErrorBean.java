package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class ErrorBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;

    public ErrorBean() {

    }

}
