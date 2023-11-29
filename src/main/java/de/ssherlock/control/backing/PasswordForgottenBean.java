package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the passwordForgotten.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class PasswordForgottenBean {

    /**
     * The logger for logging events.
     */
    private final SerializableLogger logger;

    /**
     * The service for user-related operations.
     */
    private final UserService userService;

    /**
     * The email address for password reset.
     */
    private String username;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger      The logger for logging events (Injected).
     * @param userService The service for user-related operations (Injected).
     */
    @Inject
    public PasswordForgottenBean(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
    }

    /**
     * Requests a password reset for the provided email address.
     */
    public void requestPasswordReset() {
        try {
            userService.sendPasswordForgottenEmail(username);
        } catch (BusinessNonExistentUserException e) {
            Notification notification = new Notification("The current User doesnt exist", NotificationType.ERROR);
            notification.generateUIMessage();
        }
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
     * Gets email address.
     *
     * @return the email address
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the email address for password reset.
     *
     * @param username The email address for password reset.
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
