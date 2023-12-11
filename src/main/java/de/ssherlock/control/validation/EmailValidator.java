package de.ssherlock.control.validation;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
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
 * Handles validation of email addresses.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "emailValidator", managed = true)
public class EmailValidator implements Validator<String> {

  /** The logger instance for this class. */
  private final SerializableLogger logger;

  /** The user service for user-related operations. */
  private final UserService userService;

  /** The system service for system-related operations. */
  private final SystemService systemService;

  /**
   * Constructs an EmailValidator.
   *
   * @param logger The logger instance for this class.
   * @param userService The user service for user-related operations.
   * @param systemService The system service for system-related operations.
   */
  @Inject
  public EmailValidator(
      SerializableLogger logger, UserService userService, SystemService systemService) {
    this.logger = logger;
    this.userService = userService;
    this.systemService = systemService;
  }

  /**
   * Validates an email address for uniqueness and format.
   *
   * @param facesContext The FacesContext for the current request.
   * @param uiComponent The UIComponent associated with the component being validated.
   * @param emailAddress The email address to be validated.
   * @throws ValidatorException if the validation fails.
   */
  @Override
  public void validate(FacesContext facesContext, UIComponent uiComponent, String emailAddress)
      throws ValidatorException {
    User user = new User();
    user.setEmail(emailAddress);
    if (userService.emailExists(user)) {
      FacesMessage facesMessage =
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email is already taken.", null);
      throw new ValidatorException(facesMessage);
    }
    /*
    String emailRegex = systemService.getSystemSettings().getEmailRegex();
    if (!emailAddress.matches(emailRegex)) {
      FacesMessage facesMessage =
          new FacesMessage(
              FacesMessage.SEVERITY_ERROR, "Email does not correspond to the email pattern.", null);
      throw new ValidatorException(facesMessage);
    }
     */
  }
}
