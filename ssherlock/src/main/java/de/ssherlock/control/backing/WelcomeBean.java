package de.ssherlock.control.backing;

import java.util.logging.Logger;

public class WelcomeBean {

    public WelcomeBean() {}
    private String welcomeHeading;
    private String welcomeText;
    private String email;
    private String password;
    private Logger logger;

    public String getWelcomeHeading() {
        return welcomeHeading;
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
