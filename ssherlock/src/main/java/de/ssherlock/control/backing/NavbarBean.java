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

    @Inject
    private Logger logger;

    @Inject
    private AppSession appSession;

    public NavbarBean() {

    }

    @PostConstruct
    public void initialize() {
        toggleElementVisibility();
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

    public void logout() {

    }

    private void toggleElementVisibility() {

    }

}
