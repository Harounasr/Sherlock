package de.ssherlock.control.backing;

import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Backing bean for the login.xhtml facelet.
 */
@Named
@ViewScoped
public class LoginBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The Service for user-related operations.
     */
    private final UserService userService;

    /**
     * The loginInfo entered by the user.
     */
    private LoginInfo loginInfo;

    /**
     * The unhashed password entered by the user.
     */
    private String unhashedPassword;

    /**
     * Constructor for LoginBean.
     *
     * @param userService The UserService for user-related operations (Injected).
     * @param appSession  The active session (Injected).
     * @param logger      The logger for this class (Injected).
     * @param loginInfo   The login information entered by the user (Injected empty).
     */
    @Inject
    public LoginBean(UserService userService, AppSession appSession, SerializableLogger logger, LoginInfo loginInfo) {
        this.userService = userService;
        this.appSession = appSession;
        this.logger = logger;
        this.loginInfo = loginInfo;
    }

    /**
     * Method to log in the user.
     *
     * @return The destination view after successful login.
     */
    public String login() throws LoginFailedException {
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "Login for user " + loginInfo.getUsername() + " was successful.");
            appSession.setUser(user);
            logger.log(Level.INFO, "logged in");
            return "/view/registered/courses.xhtml";
        } catch (LoginFailedException e) {
            logger.log(Level.INFO, "Incorrect password for user " + loginInfo.getUsername() + " .");
            Notification notification = new Notification(Notification.WRONG_PASSWORD_MSG, NotificationType.ERROR);
            notification.generateUIMessage();
            return "";
        }
    }

    /**
     * Redirects to the registration page.
     *
     * @return The destination view for registration.
     */
    public String registerClicked() {
        return "/view/public/registration.xhtml";
    }

    /**
     * Gets login info.
     *
     * @return the login info
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * Sets login info.
     *
     * @param loginInfo the login info
     */
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    /**
     * Gets unhashed password.
     *
     * @return the unhashed password
     */
    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    /**
     * Sets unhashed password.
     *
     * @param unhashedPassword the unhashedd password
     */
    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }
}
