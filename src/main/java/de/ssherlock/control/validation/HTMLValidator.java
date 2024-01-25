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
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param s            The HTML String to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, String s) throws ValidatorException {
        if (s == null || s.isEmpty()) {
            logger.info("The String is valid HTML as it is empty.");
            return;
        }
        if (s.toLowerCase().contains("<html")) {
            logger.warning("The String is not valid HTML body.");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "HTML tag detected. There should be no outer html, only the contents of the body.", null);
            throw new ValidatorException(message);
        } else if (s.toLowerCase().contains("<head>")) {
            logger.warning("The String is not valid HTML body.");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "HEAD tag detected. There should be no outer html, only the contents of the body.", null);
            throw new ValidatorException(message);
        } else if (s.toLowerCase().contains("<body")) {
            logger.warning("The String is not valid HTML body.");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "BODY tag detected. There should be no outer html, only the contents of the body.", null);
            throw new ValidatorException(message);
        }
        try {
            Jsoup.parse(s);
        } catch (Error e) {
            logger.warning("The HTML string could not be parsed.");
            FacesMessage message = new FacesMessage("The HTML string could not be parsed and is therefore invalid.");
            throw new ValidatorException(message);
        }
        logger.info("The String is valid HTML.");
    }
}
