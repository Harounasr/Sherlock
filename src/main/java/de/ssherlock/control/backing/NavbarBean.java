package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;

/**
 * Backing bean for the navbar.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class NavbarBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The logger for this class. */
  private final SerializableLogger logger;

  /** The active session. */
  private final AppSession appSession;

  /** The system service for system-related operations. */
  private final SystemService systemService;
    /**
     * Boolean which list to display.
     */
    private boolean seeAllCourses;


    /** The current system settings. */
  private SystemSettings systemSettings;



  /**
   * Constructor for NavbarBean.
   *
   * @param logger The logger for logging events (Injected).
   * @param appSession The active session (Injected).
   * @param systemService The system service for system-related operations.
   */
  @Inject
  public NavbarBean(SerializableLogger logger, AppSession appSession, SystemService systemService) {
    this.logger = logger;
    this.appSession = appSession;
    this.systemService = systemService;
  }

  /** Initializes the NavbarBean. */
  @PostConstruct
  public void initialize() {}

  /** Logs out the current user. */
  public String logout() {
   return appSession.logout();
  }

  /**
   * Navigates to the page displaying all courses.
   *
   * @return The destination view for all courses.
   */
  public String navigateToAllCourses() {
    seeAllCourses = true;
    return "/view/registered/coursePagination.xhtml?all=true";
  }

  /**
   * Navigates to the page displaying user's courses.
   *
   * @return The destination view for user's courses.
   */
  public String navigateToMyCourses() {
    seeAllCourses = false;
    return "/view/registered/coursePagination.xhtml?all=false";
  }

    /**
     * navigation String to Checker List.
     * @return String.
     */
  public String navigateToCheckers() {
    return "/view/registered/checkerList.xhtml";
  }

  /**
   * Navigates to the user's profile page.
   *
   * @return The destination view for user's profile.
   */
  public String navigateToProfile() {
    return "/view/registered/profile.xhtml";
  }

  /**
   * Navigates to the admin settings page.
   *
   * @return The destination view for admin settings.
   */
  public String navigateToAdminSettings() {
    return "/view/registered/admin.xhtml";
  }

  /**
   * Navigates to the help page.
   *
   * @return The destination view for help.
   */
  public String navigateToHelp() {
    return "/view/public/registration.xhtml";
  }

  /**
   * Gets system settings.
   *
   * @return the system settings
   */
  public SystemSettings getSystemSettings() {
    return systemSettings;
  }

  /**
   * Sets system settings.
   *
   * @param systemSettings the system settings
   */
  public void setSystemSettings(SystemSettings systemSettings) {
    this.systemSettings = systemSettings;
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
  public byte[] getLogo() {
    return systemService.getSystemSettings().getLogo();
  }

    /**
     * Getter for the boolean.
     * @return boolean
     */
    public boolean isSeeAllCourses() {
        return seeAllCourses;
    }
    /**
     * Setter for the boolean.
     * @param seeAllCourses boolean
     */

    public void setSeeAllCourses(boolean seeAllCourses) {
        this.seeAllCourses = seeAllCourses;
    }

}
