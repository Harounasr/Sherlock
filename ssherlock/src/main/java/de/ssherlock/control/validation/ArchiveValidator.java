package de.ssherlock.control.validation;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named
@Dependent
@FacesValidator(value = "archiveValidator", managed = true)
public class ArchiveValidator implements Validator<Part> {

    private final SerializableLogger logger;

    @Inject
    public ArchiveValidator(SerializableLogger logger) {
        this.logger = logger;
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Part part) throws ValidatorException {

    }
}
