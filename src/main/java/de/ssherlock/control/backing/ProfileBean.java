package de.ssherlock.control.backing;

import static java.util.logging.Level.INFO;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.FacultyService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;

/**
 * Backing bean for the profile.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class ProfileBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The logger for logging events. */
  private final SerializableLogger logger;

  /** The active session of the user. */
  private final AppSession appSession;

  /** The service for user-related operations. */
  private final UserService userService;
/** The faculty service.*/
  private final FacultyService facultyService;

  /** The user of the profile. */
  private User user;

  /** The userdata to be changed.*/
  private User changedUser;



  /** The first new password for change. */
  private String newPasswordOne;

  /** The second new password for change. */
  private String newPasswordTwo;

  /**
   * Constructor for ProfileBean.
   *
   * @param logger The logger for logging events.
   * @param appSession The active session of the user.
   * @param userService The service for user-related operations.
   *                    @param facultyService the faculty service.
   */
  @Inject
  public ProfileBean(
      SerializableLogger logger,
      AppSession appSession,
      UserService userService,
      FacultyService facultyService) {
    this.logger = logger;
    this.appSession = appSession;
    this.userService = userService;
    this.facultyService = facultyService;
  }

  /** Initializes the bean after construction. */
  @PostConstruct
  public void initialize() {
    logger.log(INFO, appSession.getUser().getUsername());
    user = appSession.getUser();
    changedUser = new User();
    changedUser.setUsername(user.getUsername());
  }

  /** Submits the password change request. */
  public void submitPasswordChange() {
    if (newPasswordTwo != null) {
      Password password = PasswordHashing.hashPassword(newPasswordTwo);
      changedUser.setPassword(password);
    } else {
      System.out.println("fucking return");
      return;
    }
    try {
      userService.updateUser(changedUser);
    } catch (BusinessNonExistentUserException e) {
      logger.log(INFO, "bean coulnd not find user");
    }
  }

  /** Submits the faculty change request. */
  public void submitFacultyChange() {
    logger.log(INFO, "faculty to be inserted: " + changedUser.getFacultyName());
    try {
      userService.updateUser(changedUser);
    } catch (BusinessNonExistentUserException e) {
      logger.log(INFO, "bean could not find user");
    }
  }

  /** Submits the system role change request. */
  public void submitSystemRoleChange() {}

  /** Deletes the user account.
   * @return to login*/
  public String deleteAccount() {
    logger.log(INFO, "bean: " + user.getUsername());
    try {
      userService.deleteUser(user);
    } catch (BusinessNonExistentUserException e) {
      logger.log(INFO, "user could not be found and was not deleted");
      return "";
    }
    logger.log(INFO, user.getUsername() + "was deleted.");

    return "view/public/login.xhtml";
  }

  /**
   * Gets user.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets user.
   *
   * @param user the user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets new password one.
   *
   * @return the new password one
   */
  public String getNewPasswordOne() {
    return newPasswordOne;
  }

  /**
   * Sets the first new password for change.
   *
   * @param newPasswordOne The first new password for change.
   */
  public void setNewPasswordOne(String newPasswordOne) {
    this.newPasswordOne = newPasswordOne;
  }

  /**
   * Gets new password two.
   *
   * @return the new password two
   */
  public String getNewPasswordTwo() {
    return newPasswordTwo;
  }

  /**
   * Sets the second new password for change.
   *
   * @param newPasswordTwo The second new password for change.
   */
  public void setNewPasswordTwo(String newPasswordTwo) {
    this.newPasswordTwo = newPasswordTwo;
  }

    /**
     * Gets the appsession.
     * @return appSession
     */
  public AppSession getAppSession() {
    return appSession;
  }


    /**
     * Gets the changed user.
     * @return changed user
     */
  public User getChangedUser() {
    return changedUser;
  }

    /**
     * Gets the facultyservice.
     * @return facultyservice
     */
  public FacultyService getFacultyService() {
    return facultyService;
  }
}
