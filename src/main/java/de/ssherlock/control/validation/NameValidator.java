package de.ssherlock.control.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

/**
 * Handles name validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "nameValidator", managed = true)
public class NameValidator implements Validator<String> {

    /** The minimum length for a name. */
    private static final int MINNAMELENGTH = 5;

    /**
     * The maximum length for a name.
     */
    private static final int MAXNAMELENGTH = 50;

    /**
     * Constructs an UsernameValidator.
     */

    public NameValidator() {
    }

    /**
     * Validates the username for uniqueness and format.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent The UIComponent associated with the component being validated.
     * @param name The username string to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String name)
            throws ValidatorException {
        if (name.length() < MINNAMELENGTH || name.length() > MAXNAMELENGTH) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name musst be between 5 and 50 long.", null);
            throw new ValidatorException(facesMessage);
        }
    }
}
