package de.ssherlock.control.validation;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Handles validation of HTML.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "htmlValidator", managed = true)
public class HTMLValidator implements Validator<String> {

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;


    /**
     * Constructs a HTMLValidator.
     *
     * @param logger The logger.
     */
    @Inject
    public HTMLValidator(SerializableLogger logger) {
        this.logger = logger;
    }

    /**
     * Validates the syntax of an HTML string.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent The UIComponent associated with the component being validated.
     * @param s The HTML String to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String s) throws ValidatorException {
        if (s == null || s.isEmpty()) {
            logger.info("The String is valid HTML as it is empty.");
            return;
        }
        Document document = Jsoup.parse(s);
        if (!document.html().contains("<html")) {
            logger.warning("The String is not valid HTML.");
            FacesMessage message = new FacesMessage("Invalid HTML content.");
            throw new ValidatorException(message);
        }
        logger.info("The String is valid HTML.");
    }
}
