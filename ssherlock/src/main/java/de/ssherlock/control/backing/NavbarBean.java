package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing bean for the navbar.xhtml facelet.
 */
@Named
@SessionScoped
public class NavbarBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Represents the logo as a byte string.
     */
    private String logoByteString;

    /**
     * Constructor for NavbarBean.
     *
     * @param logger     The logger for logging events (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public NavbarBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Initializes the NavbarBean.
     */
    @PostConstruct
    public void initialize() {
   }

    /**
     * Logs out the current user.
     */
    public void logout() {

    }

    /**
     * Navigates to the page displaying all courses.
     *
     * @return The destination view for all courses.
     */
    public String navigateToAllCourses() {
        return "/view/registered/courses.xhtml";
    }

    /**
     * Navigates to the page displaying user's courses.
     *
     * @return The destination view for user's courses.
     */
    public String navigateToMYCourses() {
        return "/view/registered/courses.xhtml";
    }

    /**
     * Navigates to the user's profile page.
     *
     * @return The destination view for user's profile.
     */
    public String navigateToProfile() {
        logger.log(Level.INFO,"Inside navbarbean");
        return "/view/registered/profile.xhtml";
    }

    /**
     * Navigates to the admin settings page.
     *
     * @return The destination view for admin settings.
     */
    public String navigateToAdminSettings() {
        return "";
    }

    /**
     * Navigates to the help page.
     *
     * @return The destination view for help.
     */
    public String help() {
        return "";
    }

    /**
     * Retrieves the logo as a byte string.
     *
     * @return The logo as a byte string.
     */
    public String getLogoByteString() {
        return logoByteString;
    }
}
