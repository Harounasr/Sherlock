package de.ssherlock.control.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

@Named
@Dependent
@FacesValidator(value = "checkerListParameterValidator", managed = true)
public class CheckerListParameterValidator implements Validator<String> {

    /**
     * Initializes the checkerList validator.
     */
    public CheckerListParameterValidator() {}

    /**
     * Validates the checkerList Inputs.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent  The UIComponent associated with the component being validated.iComponent
     * @param input The user input.
     * @throws ValidatorException If validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String input) throws ValidatorException {
        if (input.length() > 255 || input.isEmpty()) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parameters have to be set and can't be longer than 255.", null);
            throw new ValidatorException(facesMessage);
        }
    }



}
