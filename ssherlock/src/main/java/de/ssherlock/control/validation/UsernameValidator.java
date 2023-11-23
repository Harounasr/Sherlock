package de.ssherlock.control.validation;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
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
 * Handles username validation.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
@FacesValidator(value = "usernameValidator", managed = true)
public class UsernameValidator implements Validator<String> {

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

    /**
     * The user service for user-related operations.
     */
    private final UserService userService;

    /**
     * Constructs an UsernameValidator.
     *
     * @param logger The logger instance for this class.
     * @param userService The user service for user-related operations.
     */
    @Inject
    public UsernameValidator(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
    }

    /**
     * Validates the username for uniqueness and format.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param s            The username string to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String s) throws ValidatorException {

    }
}
