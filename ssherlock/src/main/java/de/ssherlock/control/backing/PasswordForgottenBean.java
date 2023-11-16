package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the passwordForgotten.xhtml facelet.
 */
@Named
@ViewScoped
public class PasswordForgottenBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for logging events.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The service for user-related operations.
     */
    private final UserService userService;

    /**
     * The email address for password reset.
     */
    private String emailAddress;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger      The logger for logging events (Injected).
     * @param appSession  The active session (Injected).
     * @param userService The service for user-related operations (Injected).
     */
    @Inject
    public PasswordForgottenBean(SerializableLogger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Requests a password reset for the provided email address.
     */
    public void requestPasswordReset() {
        User user = new User(null, emailAddress, null, null, null, null, null, null);
        userService.sendPasswordForgottenEmail(user);
    }

    /**
     * Navigates to the login page.
     *
     * @return The destination view for the login page.
     */
    public String navigateToLogin() {
        return "";
    }

    /**
     * Sets the email address for password reset.
     *
     * @param emailAddress The email address for password reset.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
