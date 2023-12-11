package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the passwordForgotten.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class PasswordForgottenBean {

  /** The logger for logging events. */
  private final SerializableLogger logger;

  /** The service for user-related operations. */
  private final UserService userService;

  /** The user for password reset. */
  private User user;

  /**
   * Constructor for PasswordForgottenBean.
   *
   * @param logger The logger for logging events (Injected).
   * @param userService The service for user-related operations (Injected).
   */
  @Inject
  public PasswordForgottenBean(SerializableLogger logger, UserService userService) {
    this.logger = logger;
    this.userService = userService;
    User user = new User();
  }

  /** Requests a password reset for the provided email address. */
  public void requestPasswordReset() {
    try {
      userService.sendPasswordForgottenEmail(user);
    } catch (BusinessNonExistentUserException e) {
      Notification notification =
          new Notification("The current User doesnt exist", NotificationType.ERROR);
      notification.generateUIMessage();
    }
  }

  /**
   * Navigates to the login page.
   *
   * @return The destination view for the login page.
   */
  public String navigateToLogin() {
    return "";
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
}
