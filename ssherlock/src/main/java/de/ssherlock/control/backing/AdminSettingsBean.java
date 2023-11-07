package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AdminSettingsBean {
    @Inject
    private Logger logger;
    private Image logo;
    private Color prime;
    private Color second;
    private String systemName;
    private String emailRegex;

    public AdminSettingsBean() {

    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Color getPrime() {
        return prime;
    }

    public void setPrime(Color prime) {
        this.prime = prime;
    }

    public Color getSecond() {
        return second;
    }

    public void setSecond(Color second) {
        this.second = second;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getEmailRegex() {
        return emailRegex;
    }

    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

}
