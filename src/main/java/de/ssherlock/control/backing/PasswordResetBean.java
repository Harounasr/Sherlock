package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.annotations.Pos;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Backing bean for the passwordReset.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class PasswordResetBean implements Serializable {

    /** Serial Version UID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for logging events.
     */
    private final SerializableLogger logger;

    /**
     * The service for user-related operations.
     */
    private final UserService userService;

    /**
     * The new password.
     */
    private String passwordOne;

    /**
     * The repeated password.
     */
    private String passwordTwo;

    /**
     * The user for password reset.
     */
    private User user;

    /**
     * The Token given in the url.
     */
    private String token;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger      The logger for logging events (Injected).
     * @param userService The service for user-related operations (Injected).
     */
    @Inject
    public PasswordResetBean(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
        user = new User();
        logger.log(Level.INFO, "Inside PasswordResetBean");
    }

    @PostConstruct
    public void getToken() {
        Map<String, String> parameter =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!Objects.equals(parameter.get("token"), "")) {
            token = parameter.get("token");
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
     * Reset the password.
     */
    public String resetPassword() {
        user.setVerificationToken(token);
        logger.log(Level.INFO, "Token: " + token);
        user.setPassword(PasswordHashing.hashPassword(passwordOne));
        if (userService.resetPassword(user)) {
            return "/view/public/login";
        } else {
            return "";
        }
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
     * Gets the new password.
     *
     * @return The new password.
     */
    public String getPasswordOne() {
        return passwordOne;
    }

    /**
     * Sets the new password.
     *
     * @param passwordOne The new password.
     */
    public void setPasswordOne(String passwordOne) {
        this.passwordOne = passwordOne;
    }

    /**
     * Gets the repeated password.
     *
     * @return The repeated password.
     */
    public String getPasswordTwo() {
        return passwordTwo;
    }

    /**
     * Sets the repeated password.
     *
     * @param passwordTwo The repeated password.
     */
    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }
}
