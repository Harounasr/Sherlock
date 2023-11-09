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

    @Inject
    private UserService userService;
    @Inject
    private AppSession appSession;
    @Inject
    private Logger logger;

    private String welcomeHeading;

    private String welcomeText;


    private String password;

    private String username;

    public LoginBean() {
    }

    public void login() {
        LoginInfo loginInfo = new LoginInfo(username, new Password(password, "salt"));
        try {
            User user = userService.login(loginInfo);
            logger.log(Level.INFO, "logged in");
            //session.setUser(user);
        } catch (LoginFailedException e) {
            Notification notification = new Notification(Notification.WRONG_PASSWORD_MSG, NotificationType.ERROR);
            notification.generateUIMessage();
        }
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

}
