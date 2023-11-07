package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.Password;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class WelcomeBean {

    public WelcomeBean() {}

    private String welcomeHeading;

    private String welcomeText;

    private String email;

    private String password;

    private String username;
    private Logger logger;


    public String getWelcomeHeading() {
        return welcomeHeading;
    }
    public String getUsername() {
        return username;
    }

    public void login() {
        LoginInfo loginInfo = new LoginInfo(username, new Password(password, "salt"));
        UserService userService = new UserService();
        userService.verifyLogin(loginInfo);
    }

    public void setWelcomeHeading(String welcomeHeading) {
        this.welcomeHeading = welcomeHeading;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
