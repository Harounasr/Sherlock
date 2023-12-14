package de.ssherlock.control.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

/**
 * Handles validation of Timestamps.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "dateValidator", managed = true)
public class TimestampValidator implements Validator<Timestamp> {

    /**
     * Constructs a timestamp validator.
     */
    public TimestampValidator() {

    }

    /**
     * Validates a date to be in the future.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent The UIComponent associated with the component being validated.
     * @param timestamp The date to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Timestamp timestamp) throws ValidatorException {
        Calendar calendar = Calendar.getInstance();
        Instant now = calendar.toInstant();
        if (now.isAfter(timestamp.toInstant())) {
            FacesMessage facesMessage = new FacesMessage("The date has to be in the future.");
            throw new ValidatorException(facesMessage);
        }

    }
}
