package de.ssherlock.control.validation;

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

  /** The minimum length for a password. */
  private static final int MINPASSWORDLENGTH = 8;

    /** The maximum length for a password. */
    private static final int MAXPASSWORDLENGTH = 50;

  /**
   * Constructs a new PasswordValidator.
   */
  @Inject
  public PasswordValidator() {
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
    if (password == null || password.length() < MINPASSWORDLENGTH || password.length() > MAXPASSWORDLENGTH) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password has to be between 8 and 50 long.", null);
        throw new ValidatorException(facesMessage);
    }
    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&]+$")) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password must include at least one uppercase letter, "
                                                                        + "one lowercase letter, one digit, and one special character.", null);
        throw new ValidatorException(facesMessage);
    }
  }
}
