package de.ssherlock.control.validation;

import de.ssherlock.business.service.FacultyService;
import de.ssherlock.global.logging.SerializableLogger;
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

/**
 * Handles validation of faculty addition.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "facultyExistsValidator", managed = true)
public class FacultyExistsValidator implements Validator<String> {

    /** The logger instance for this class. */
    private final SerializableLogger logger;

    /** The system service for system-related operations. */
    private final FacultyService facultyService ;

    /**
     * Constructs a FacultyExistsValidator.
     *
     * @param logger The logger instance for this class.
     * @param facultyService The faculty service for faculty-related operations.
     */
    @Inject
    public FacultyExistsValidator(SerializableLogger logger, FacultyService facultyService) {
        this.logger = logger;
        this.facultyService = facultyService;
    }

    /**
     * Validates the existence of a faculty within the system.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent The UIComponent associated with the component being validated.
     * @param facultyName The faculty identifier or name to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String facultyName)
            throws ValidatorException {
        Faculty faculty =  new Faculty();
        faculty.setName(facultyName);
        if (!facultyService.facultyExists(faculty)) {
            FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faculty does not exist. " , null);
            throw new ValidatorException(facesMessage);
        }

    }
}
