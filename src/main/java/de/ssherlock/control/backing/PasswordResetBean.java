package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the passwordReset.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@RequestScoped
public class PasswordResetBean {

    /** The logger for logging events. */
    private final SerializableLogger logger;

    /** The service for user-related operations. */
    private final UserService userService;

    /** The user for password reset. */
    private User user;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger The logger for logging events (Injected).
     * @param userService The service for user-related operations (Injected).
     */
    @Inject
    public PasswordResetBean(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
        user = new User();
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
}
