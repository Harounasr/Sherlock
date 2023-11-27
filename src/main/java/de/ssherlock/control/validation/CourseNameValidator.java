package de.ssherlock.control.validation;

import de.ssherlock.business.service.CourseService;
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
 * Handles validation of course names.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "courseNameValidator", managed = true)
public class CourseNameValidator implements Validator<String> {

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

    /**
     * The course service for course-related operations.
     */
    private final CourseService courseService;

    /**
     * Default constructor.
     *
     * @param logger        The logger instance.
     * @param courseService The course service for course-related operations.
     */
    @Inject
    public CourseNameValidator(SerializableLogger logger, CourseService courseService) {
        this.logger = logger;
        this.courseService = courseService;
    }

    /**
     * Validates a course name to ensure uniqueness.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param courseName            The course name to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String courseName) throws ValidatorException {

    }
}
