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
 * Handles password validation.
 */
@Named
@Dependent
@FacesValidator(value = "passwordValidator", managed = true)
public class PasswordValidator implements Validator<String> {

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

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
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param password            The password string to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String password) throws ValidatorException {

    }
}
