package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class VerificationBean {

    private final Logger logger;
    private final AppSession session;

    @Inject
    public VerificationBean(Logger logger, AppSession session) {
        this.logger = logger;
        this.session = session;
    }

    @PostConstruct
    public void handleVerifiedRegistration() {

    }

    public String navigateToLogin() {
        return "";
    }

}
