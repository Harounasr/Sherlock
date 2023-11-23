package de.ssherlock.control.validation;

import de.ssherlock.business.service.SystemService;
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
 * Handles validation of faculty addition.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "facultyExistsValidator", managed = true)
public class FacultyExistsValidator implements Validator<String> {

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

    /**
     * The system service for system-related operations.
     */
    private final SystemService systemService;

    /**
     * Constructs a FacultyExistsValidator
     *
     * @param logger The logger instance for this class.
     * @param systemService The system service for system-related operations.
     */
    @Inject
    public FacultyExistsValidator(SerializableLogger logger, SystemService systemService) {
        this.logger = logger;
        this.systemService = systemService;
    }

    /**
     * Validates the existence of a faculty within the system.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param faculty            The faculty identifier or name to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String faculty) throws ValidatorException {

    }
}
