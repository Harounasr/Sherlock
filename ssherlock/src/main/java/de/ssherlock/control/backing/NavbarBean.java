package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class NavbarBean {

    private final Logger logger;
    private final AppSession appSession;

    private String logoByteString;

    @Inject
    public NavbarBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    @PostConstruct
    public void initialize() {
        toggleElementVisibility();
    }

    public void logout() {

    }

    private void toggleElementVisibility() {

    }
    public String navigateToAllCourses() {
        return "";
    }

    public String navigateToMYCourses() {
        return "";
    }

    public String navigateToProfile() {
        return "";
    }

    public String navigateToAdminSettings() {
        return "";
    }

    public String help() {
        return "";
    }

    public String getLogoByteString() {
        return logoByteString;
    }

    public void setLogoByteString(String logoByteString) {
        this.logoByteString = logoByteString;
    }
}
