package de.ssherlock.control.validation;

import de.ssherlock.business.service.UserService;
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
 * Handles username validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "usernameValidator", managed = true)
public class UsernameValidator implements Validator<String> {

  /** The logger instance for this class. */
  private final SerializableLogger logger;

  /** The user service for user-related operations. */
  private final UserService userService;

  /** The minimum length for a password. */
  private final int MIN_USERNAME_LENGTH = 5;

  /**
   * Constructs an UsernameValidator.
   *
   * @param logger The logger instance for this class.
   * @param userService The user service for user-related operations.
   */
  @Inject
  public UsernameValidator(SerializableLogger logger, UserService userService) {
    this.logger = logger;
    this.userService = userService;
  }

  /**
   * Validates the username for uniqueness and format.
   *
   * @param facesContext The FacesContext for the current request.
   * @param uiComponent The UIComponent associated with the component being validated.
   * @param username The username string to be validated.
   * @throws ValidatorException if the validation fails.
   */
  @Override
  public void validate(FacesContext facesContext, UIComponent uiComponent, String username)
      throws ValidatorException {
    if (userService.userNameExists(username)) {
      FacesMessage facesMessage =
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is already taken.", null);
      throw new ValidatorException(facesMessage);
    }
    if (username.length() < MIN_USERNAME_LENGTH) {
      FacesMessage facesMessage =
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username musst be at least 5 long.", null);
      throw new ValidatorException(facesMessage);
    }
  }
}
