package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Base64;

/**
 * Backing bean for the passwordForgotten.xhtml facelet.
 *
 * @author Leon Höfling
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
     * The system service.
     */
    private final SystemService systemService;

    /**
     * The user for password reset.
     */
    private User user;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger        The logger for logging events (Injected).
     * @param userService   The service for user-related operations (Injected).
     * @param systemService The system service.
     */
    @Inject
    public PasswordForgottenBean(SerializableLogger logger, UserService userService, SystemService systemService) {
        this.logger = logger;
        this.userService = userService;
        this.systemService = systemService;
        user = new User();
    }

    /**
     * Requests a password reset for the provided email address.
     */
    public void requestPasswordReset() {
        try {
            userService.sendPasswordForgottenEmail(user);
        } catch (BusinessNonExistentUserException e) {
            Notification notification =
                    new Notification("The current User doesnt exist", NotificationType.ERROR);
            notification.generateUIMessage();
        }
    }

    /**
     * Navigates to the login page.
     *
     * @return The destination view for the login page.
     */
    public String navigateToLogin() {
        return "/view/public/login.xhtml";
    }

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user password reset.
     *
     * @param user The user for the password reset.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the logo in base 64.
     *
     * @return The logo.
     */
    public String getLogoBase64() {
        return Base64.getEncoder().encodeToString(systemService.getSystemSettings().getLogo());
    }
}
