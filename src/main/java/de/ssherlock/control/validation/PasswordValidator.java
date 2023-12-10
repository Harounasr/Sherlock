package de.ssherlock.control.validation;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Handles password validation.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "passwordValidator", managed = true)
public class PasswordValidator implements Validator<String> {

  /** The logger instance for this class. */
  private final SerializableLogger logger;

  /** The minimum length for a password. */
  private final int MIN_PASSWORD_LENGTH = 8;

  /**
   * Constructs a new PasswordValidator.
   *
   * @param logger The logger instance for this class.
   */
  @Inject
  public PasswordValidator(SerializableLogger logger) {
    this.logger = logger;
  }

  /**
   * Validates the password based on specified criteria.
   *
   * @param facesContext The FacesContext for the current request.
   * @param uiComponent The UIComponent associated with the component being validated.
   * @param password The password string to be validated.
   * @throws ValidatorException if the validation fails.
   */
  @Override
  public void validate(FacesContext facesContext, UIComponent uiComponent, String password)
      throws ValidatorException {
    if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
      FacesMessage facesMessage =
          new FacesMessage(
              FacesMessage.SEVERITY_ERROR, "Password has to be at least 8 long.", null);
      throw new ValidatorException(facesMessage);
    }
    // Check complexity (at least one uppercase, one lowercase, one digit, one special character)
    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")) {
      FacesMessage message =
          new FacesMessage(
              FacesMessage.SEVERITY_ERROR,
              "Password must include at least one uppercase letter, one lowercase letter, one digit, and one special character.",
              null);
      throw new ValidatorException(message);
    }
  }
}
