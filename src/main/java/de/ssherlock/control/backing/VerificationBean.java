package de.ssherlock.control.backing;

import de.ssherlock.global.logging.SerializableLogger;
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
  private static final String VERIFICATION_TEXT = "Thank you for registration.";

  /**
   * Constructor for VerificationBean.
   *
   * @param logger The logger for this class.
   */
  @Inject
  public VerificationBean(SerializableLogger logger) {
    this.logger = logger;
  }

  /** Handles actions after a verified registration. */
  @PostConstruct
  public void handleVerifiedRegistration() {
    Map<String, String> parameter =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    logger.log(Level.INFO, "parameter = " + parameter.get("id"));
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
}
