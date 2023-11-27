package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Backing bean for the login.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class LoginBean {

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
     * Constructor for LoginBean.
     *
     * @param userService The UserService for user-related operations (Injected).
     * @param logger      The logger for this class (Injected).
     * @param appSession  The active session.
     * @param loginInfo   The login information entered by the user (Injected empty).
     */
    @Inject
    public LoginBean(UserService userService, SerializableLogger logger, AppSession appSession, LoginInfo loginInfo) {
        this.userService = userService;
        this.logger = logger;
        this.appSession = appSession;
        this.loginInfo = loginInfo;
    }

    /**
     * Method to log in the user.
     *
     * @return The destination view after successful login.
     *
     * @throws LoginFailedException when the login fails.
     */
    public String login() throws LoginFailedException {
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "Login for user " + loginInfo.getUsername() + " was successful.");
            appSession.setUser(user);
            return "/view/registered/coursePagination.xhtml";
        } catch (LoginFailedException | BusinessNonExistentUserException e) {
            logger.log(Level.INFO, "Incorrect password for user " + loginInfo.getUsername() + "");
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
     * Redirects the user to the password forgotten page.
     *
     * @return The navigation outcome.
     */
    public String passwordForgottenClicked() {
        return "/view/public/passwordForgotten.xhtml";
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

}
