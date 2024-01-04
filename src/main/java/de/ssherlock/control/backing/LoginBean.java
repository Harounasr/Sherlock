package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Base64;
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
     * The system service.
     */
    private final SystemService systemService;

    /**
     * The loginInfo entered by the user.
     */
    private LoginInfo loginInfo;

    /**
     * Constructor for LoginBean.
     *
     * @param userService   The UserService for user-related operations (Injected).
     * @param logger        The logger for this class (Injected).
     * @param systemService The system service.
     * @param appSession    The active session.
     */
    @Inject
    public LoginBean(
            UserService userService,
            SerializableLogger logger,
            SystemService systemService,
            AppSession appSession) {
        this.userService = userService;
        this.logger = logger;
        this.appSession = appSession;
        this.systemService = systemService;
        this.loginInfo = new LoginInfo();
    }

    /**
     * Method to log in the user.
     *
     * @return The destination view after successful login.
     * @throws LoginFailedException when the login fails.
     */
    public String login() throws LoginFailedException {
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "Login for user " + loginInfo.getUsername() + " was successful.");
            appSession.setUser(user);
            return "/view/registered/coursePagination.xhtml?faces-redirect=true&all=true";
        } catch (LoginFailedException | BusinessNonExistentUserException e) {
            logger.log(Level.INFO, "Incorrect password for user " + loginInfo.getUsername());
            Notification notification =
                    new Notification(Notification.WRONG_PASSWORD_MSG, NotificationType.ERROR);
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

    /**
     * Gets the logo in base 64.
     *
     * @return The logo.
     */
    public String getLogoBase64() {
        return Base64.getEncoder().encodeToString(systemService.getSystemSettings().getLogo());
    }
}
