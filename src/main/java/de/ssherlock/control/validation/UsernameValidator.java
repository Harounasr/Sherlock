package de.ssherlock.control.validation;

import de.ssherlock.business.service.UserService;
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
 * Handles username validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "usernameValidator", managed = true)
public class UsernameValidator implements Validator<String> {

  /** The user service for user-related operations. */
  private final UserService userService;

  /** The minimum length for a username. */
  private static final int MINUSERNAMELENGTH = 5;

    /**
     * The maximum length for a username.
     */
    private static final int MAXUSERNAMELENGTH = 50;

  /**
   * Constructs an UsernameValidator.
   *
   * @param userService The user service for user-related operations.
   */
  @Inject
  public UsernameValidator(UserService userService) {
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
    User user = new User();
    user.setUsername(username);
    if (userService.userNameExists(user)) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is already taken.", null);
      throw new ValidatorException(facesMessage);

    }
    if (username.length() < MINUSERNAMELENGTH || username.length() > MAXUSERNAMELENGTH) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username musst be between 5 and 50 long.", null);
        throw new ValidatorException(facesMessage);
    }
  }
}
