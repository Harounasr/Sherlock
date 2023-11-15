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
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class LoginBean {

    private final UserService userService;
    private final AppSession appSession;
    private final Logger logger;

    private String welcomeHeading;
    private String welcomeText;
    private String password;
    private String username;

    @Inject
    public LoginBean(UserService userService, AppSession appSession, Logger logger) {
        this.userService = userService;
        this.appSession = appSession;
        this.logger = logger;
    }

    public String login() {
        LoginInfo loginInfo = new LoginInfo(username, new Password(password, "salt"));
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "logged in");
            appSession.setUser(user);
            //Notification notification = new Notification("You have been logged in.", NotificationType.SUCCESS);
            //notification.generateUIMessage();
            return "/view/courses.xhtml";
        } catch (LoginFailedException e) {
            Notification notification = new Notification(Notification.WRONG_PASSWORD_MSG, NotificationType.ERROR);
            notification.generateUIMessage();
            return "";
        }
    }

    public String registerClicked() {
        return "/view/registration.xhtml";
    }
    public String imageUploadClicked() {
        return "/view/imageUploadPrototype.xhtml";
    }
    public String getWelcomeHeading() {
        return welcomeHeading;
    }

    public void setWelcomeHeading(String welcomeHeading) {
        this.welcomeHeading = welcomeHeading;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String fileUploadClicked() {
        return "/view/FileUpload.xhtml";
    }

}
