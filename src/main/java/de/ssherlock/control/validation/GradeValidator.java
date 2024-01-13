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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles testate comment validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "gradeValidator", managed = true)
public class GradeValidator implements Validator<Integer> {

    /**
     * The possible grades.
     */
    private final List<Integer> grades = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

    /**
     * Constructs an TestateCommentValidator.
     */
    @Inject
    public GradeValidator() {}

    /**
     * Validates the grade selection.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Integer selection)
            throws ValidatorException {

        if (!grades.contains(selection)) {
            FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a grade.", null);
            throw new ValidatorException(facesMessage);
        }
    }

}
