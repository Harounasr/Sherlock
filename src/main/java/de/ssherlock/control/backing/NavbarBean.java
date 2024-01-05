package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Base64;
import java.util.Objects;

/**
 * Backing bean for the navbar.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class NavbarBean implements Serializable {

    /**
     * Serial Version UID.
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
     * The system service for system-related operations.
     */
    private final SystemService systemService;

    /**
     * The current facelet as a string.
     */
    private String currentFacelet;

    /**
     * Constructor for NavbarBean.
     *
     * @param logger        The logger for logging events (Injected).
     * @param appSession    The active session (Injected).
     * @param systemService The system service for system-related operations.
     */
    @Inject
    public NavbarBean(SerializableLogger logger, AppSession appSession, SystemService systemService) {
        this.logger = logger;
        this.appSession = appSession;
        this.systemService = systemService;
    }

    /**
     * Initializes the NavbarBean.
     */
    @PostConstruct
    public void initialize() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        currentFacelet = viewId.substring(viewId.lastIndexOf('/') + 1).split("\\.", 2)[0];
        logger.info(currentFacelet);
    }

    /**
     * Logs out the current user.
     *
     * @return String to Login Page
     */
    public String logout() {
        return appSession.logout();
    }

    /**
     * Navigates to the page displaying all courses.
     *
     * @return The destination view for all courses.
     */
    public String navigateToAllCourses() {
        return "/view/registered/coursePagination.xhtml?faces-redirect=true&all=true";
    }

    /**
     * Navigates to the page displaying user's courses.
     *
     * @return The destination view for user's courses.
     */
    public String navigateToMyCourses() {
        return "/view/registered/coursePagination.xhtml?faces-redirect=true&all=false";
    }

    /**
     * navigation String to Checker List.
     *
     * @return String.
     */
    public String navigateToCheckers() {
        return "/view/registered/checkerList.xhtml?faces-redirect=true";
    }

    /**
     * Navigates to the user's profile page.
     *
     * @return The destination view for user's profile.
     */
    public String navigateToProfile() {
        return "/view/registered/profile.xhtml?faces-redirect=true";
    }

    /**
     * Navigates to the admin settings page.
     *
     * @return The destination view for admin settings.
     */
    public String navigateToAdminSettings() {
        return "/view/admin/admin.xhtmlfaces-redirect=true";
    }

    /**
     * Navigates to the help page.
     *
     * @return The destination view for help.
     */
    public String navigateToHelp() {
        return "/view/public/registration.xhtml?faces-redirect=true";
    }

    /**
     * Getter for the appSession.
     *
     * @return AppSession
     */
    public AppSession getAppSession() {
        return appSession;
    }

    /**
     * Getter for the logo, stored in the database.
     *
     * @return byteArray for the Logo.
     */
    public String getLogoBase64() {
        return Base64.getEncoder().encodeToString(systemService.getSystemSettings().getLogo());
    }

    /**
     * Is my courses boolean.
     *
     * @return the boolean
     */
    public boolean isMyCourses() {
        boolean all = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("all"));
        return Objects.equals(currentFacelet, "coursePagination") && !all;
    }

    /**
     * Is all courses boolean.
     *
     * @return the boolean
     */
    public boolean isAllCourses() {
        boolean all = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("all"));
        return Objects.equals(currentFacelet, "coursePagination") && all;
    }

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
        return Objects.equals(currentFacelet, "admin");
    }

    /**
     * Is help boolean.
     *
     * @return the boolean
     */
    public boolean isHelp() {
        return Objects.equals(currentFacelet, "help");
    }

    /**
     * Is profile boolean.
     *
     * @return the boolean
     */
    public boolean isProfile() {
        return Objects.equals(currentFacelet, "profile");
    }
}
