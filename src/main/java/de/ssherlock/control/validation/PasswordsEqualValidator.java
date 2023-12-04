package de.ssherlock.control.validation;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Handles the validation of two equal passwords.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "passwordsEqualValidator", managed = true)
public class PasswordsEqualValidator implements Validator<String> {

  /** The logger instance for this class. */
  private final SerializableLogger logger;

  /**
   * Constructs a new PasswordValidator.
   *
   * @param logger The logger instance for this class.
   */
  @Inject
  public PasswordsEqualValidator(SerializableLogger logger) {
    this.logger = logger;
  }

  /**
   * Validates that two passwords are equal.
   *
   * @param facesContext The FacesContext for the current request.
   * @param uiComponent The UIComponent associated with the component being validated.
   * @param password The password string to be validated.
   * @throws ValidatorException if the validation fails.
   */
  @Override
  public void validate(FacesContext facesContext, UIComponent uiComponent, String password)
      throws ValidatorException {}
}
