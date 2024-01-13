package de.ssherlock.control.validation;

import de.ssherlock.business.service.FacultyService;
import de.ssherlock.global.transport.Faculty;
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
import java.util.List;

/**
 * Handles faculty validation in registration.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "facultyValidator", managed = true)
public class FacultyValidator implements Validator<String> {

    /**
     * The possible faculties.
     */
    private final List<String> faculties;

    /**
     * Constructs a FacultyValidator.
     *
     * @param facultyService The faculty service.
     */
    @Inject
    public FacultyValidator(FacultyService facultyService) {
        List<Faculty> facs = facultyService.getFaculties();
        faculties = new ArrayList<>();
        for (Faculty faculty : facs) {
            faculties.add(faculty.getName());
        }
    }

    /**
     * Validates the faculty selection.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String selection)
            throws ValidatorException {
        if (!faculties.contains(selection)) {
            FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a faculty.", null);
            throw new ValidatorException(facesMessage);
        }
    }

}
