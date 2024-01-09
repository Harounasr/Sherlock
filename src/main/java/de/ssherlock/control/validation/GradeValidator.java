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
 * Handles testate comment validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "gradeValidator", managed = true)
public class GradeValidator implements Validator<String> {

    /**
     * Constructs an TestateCommentValidator.
     */
    @Inject
    public GradeValidator() {}

    /**
     * Validates the username for uniqueness and format.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String selection)
            throws ValidatorException {

        if (selection == null || selection.trim().isEmpty()) {
            FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a grade.", null);
            throw new ValidatorException(facesMessage);
        }
        FacesMessage facesMessage =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a grade.", null);
        throw new ValidatorException(facesMessage);
    }

}
