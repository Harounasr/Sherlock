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

@Named
@Dependent
@FacesValidator(value = "emailValidator", managed = true)
public class EmailValidator implements Validator<String> {

    private final SerializableLogger logger;
    private final UserService userService;
    private final SystemService systemService;

    @Inject
    public EmailValidator(SerializableLogger logger, UserService userService, SystemService systemService) {
        this.logger = logger;
        this.userService = userService;
        this.systemService = systemService;
    }


    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String s) throws ValidatorException {

    }


}
