package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class AdminBean {
    private Logger logger;
    private AppSession appSession;

    @Inject
    public AdminBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }
}
