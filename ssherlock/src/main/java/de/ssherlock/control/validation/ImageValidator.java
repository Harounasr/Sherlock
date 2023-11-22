package de.ssherlock.control.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named
@Dependent
@FacesValidator(value = "imageValidator", managed = true)
public class ImageValidator implements Validator<Part> {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Part part) throws ValidatorException {

    }
}
