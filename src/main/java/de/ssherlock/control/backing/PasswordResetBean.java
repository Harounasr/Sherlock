package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for the passwordReset.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@RequestScoped
public class PasswordResetBean {

  /** The logger for logging events. */
  private final SerializableLogger logger;

  /** The service for user-related operations. */
  private final UserService userService;

  /** The new password. */
  private String passwordOne;

  /** The repeated password. */
  private String passwordTwo;

  /** The user for password reset. */
  private User user;

  /**
   * Constructor for PasswordForgottenBean.
   *
   * @param logger The logger for logging events (Injected).
   * @param userService The service for user-related operations (Injected).
   */
  @Inject
  public PasswordResetBean(SerializableLogger logger, UserService userService) {
    this.logger = logger;
    this.userService = userService;
    user = new User();
  }

  /**
   * Navigates to the login page.
   *
   * @return The destination view for the login page.
   */
  public String navigateToLogin() {
    return "/view/public/login.xhtml";
  }

  /** Reset the password. */
  public void resetPassword() {
    Map<String, String> parameter =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    user.setVerificationToken(parameter.get("token"));
    try {
      userService.updateUser(user);
    } catch (BusinessNonExistentUserException e) {
      logger.log(Level.INFO, "No such user.");
    }
  }

  /**
   * Gets the user.
   *
   * @return the user.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user password reset.
   *
   * @param user The user for the password reset.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the new password.
   *
   * @return The new password.
   */
  public String getPasswordOne() {
    return passwordOne;
  }

  /**
   * Sets the new password.
   *
   * @param passwordOne The new password.
   */
  public void setPasswordOne(String passwordOne) {
    this.passwordOne = passwordOne;
  }

  /**
   * Gets the repeated password.
   *
   * @return The repeated password.
   */
  public String getPasswordTwo() {
    return passwordTwo;
  }

  /**
   * Sets the repeated password.
   *
   * @param passwordTwo The repeated password.
   */
  public void setPasswordTwo(String passwordTwo) {
    this.passwordTwo = passwordTwo;
  }
}
