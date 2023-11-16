package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the verification.xhtml facelet.
 */
@Named
@ViewScoped
public class VerificationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession session;

    /**
     * Constructor for VerificationBean.
     *
     * @param logger  The logger for this class.
     * @param session The active session.
     */
    @Inject
    public VerificationBean(Logger logger, AppSession session) {
        this.logger = logger;
        this.session = session;
    }

    /**
     * Handles actions after a verified registration.
     */
    @PostConstruct
    public void handleVerifiedRegistration() {

    }

    /**
     * Navigates to the login page.
     *
     * @return The navigation outcome.
     */
    public String navigateToLogin() {
        return "";
    }
}
