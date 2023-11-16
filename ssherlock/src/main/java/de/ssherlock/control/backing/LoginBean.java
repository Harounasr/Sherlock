package de.ssherlock.control.backing;

import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The Service for user-related operations.
     */
    private final UserService userService;

    /**
     * The password entered by the user.
     */
    private String password;

    /**
     * The username entered by the user.
     */
    private String username;

    /**
     * Constructor for LoginBean.
     *
     * @param userService The UserService for user-related operations (Injected).
     * @param appSession  The active session (Injected).
     * @param logger      The logger for this class (Injected).
     */
    @Inject
    public LoginBean(UserService userService, AppSession appSession, Logger logger) {
        this.userService = userService;
        this.appSession = appSession;
        this.logger = logger;
    }

    /**
     * Method to log in the user.
     *
     * @return The destination view after successful login.
     */
    public String login() {
        LoginInfo loginInfo = new LoginInfo(username, new Password(password, "salt"));
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "logged in");
            appSession.setUser(user);
            return "/view/courses.xhtml";
        } catch (LoginFailedException e) {
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
        return "/view/registration.xhtml";
    }

    /**
     * Redirects to the image upload prototype page.
     *
     * @return The destination view for image upload.
     */
    public String imageUploadClicked() {
        return "/view/imageUploadPrototype.xhtml";
    }

    /**
     * Redirects to the file upload page.
     *
     * @return The destination view for file upload.
     */
    public String fileUploadClicked() {
        return "/view/FileUpload.xhtml";
    }

    /**
     * Sets the username entered by the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password entered by the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
