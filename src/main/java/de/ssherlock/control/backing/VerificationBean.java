package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for the verification.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@RequestScoped
public class VerificationBean {

  /** Logger for this class. */
  private final SerializableLogger logger;

  /** The text which is displayed on the verification page. */
  private static final String VERIFICATIONTEXT = "Thank you for registration.";

  /** The Service for user-related operations. */
  private final UserService userService;

  /**
   * Constructor for VerificationBean.
   *
   * @param logger The logger for this class.
   * @param userService The UserService for user-related operations (Injected).
   */
  @Inject
  public VerificationBean(SerializableLogger logger, UserService userService) {
    this.logger = logger;
    this.userService = userService;
  }

  /** Handles actions after a verified registration. */
  @PostConstruct
  public void handleVerifiedRegistration() {
    Map<String, String> parameter =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    logger.log(Level.INFO, "parameter = " + parameter.get("token"));
    User user = new User();
    user.setVerificationToken(parameter.get("token"));
    userService.verifyUser(user);
  }

  /**
   * Navigates to the login page.
   *
   * @return The navigation outcome.
   */
  public String navigateToLogin() {
    logger.log(Level.INFO, "Back to login");
    return "/view/public/login.xhtml?faces-redirect=true";
  }

  /**
   * Gets the verification text.
   *
   * @return The verification text.
   */
  public String getVerificationText() {
    return VERIFICATIONTEXT;
  }
}
