package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Level;

/**
 * Backing bean for the registration.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class RegistrationBean {

  /** The logger for this class. */
  private final SerializableLogger logger;

  /** The service handling user-related operations. */
  private final UserService userService;

  /** The user that is to be registered. */
  private User user;

  /** The unhashed password entered by the user. */
  private String unhashedPassword;

  /**
   * Constructs a new Registration bean.
   *
   * @param logger The logger for this class (Injected).
   * @param userService The service for user-based operations (Injected).
   * @param user The user that is to be registered (Injected empty).
   */
  @Inject
  public RegistrationBean(SerializableLogger logger, UserService userService, User user) {
    this.logger = logger;
    this.userService = userService;
    this.user = user;
  }

  /** Tries to register a new user using the provided information. */
  public void register() {
    Password password = PasswordHashing.getHashedPassword(unhashedPassword);
    user.setPassword(password);
    userService.registerUser(user);
  }

  /**
   * Navigates to the login page.
   *
   * @return String representing the navigation outcome.
   */
  public String navigateToLogin() {
    logger.log(Level.INFO, "Login");
    return "/view/public/login.xhtml";
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
   * Gets unhashed password.
   *
   * @return the unhashed password
   */
  public String getUnhashedPassword() {
    return unhashedPassword;
  }

  /**
   * Sets unhashed password.
   *
   * @param unhashedPassword the unhashed password
   */
  public void setUnhashedPassword(String unhashedPassword) {
    this.unhashedPassword = unhashedPassword;
  }
}
