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
import jakarta.servlet.http.Part;

/**
 * Handles validation of uploaded images.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "imageValidator", managed = true)
public class ImageValidator implements Validator<Part> {

  /** The logger instance for this class. */
  private final SerializableLogger logger;

  /**
   * Constructs a new image validator.
   *
   * @param logger The logger instance for this class.
   */
  @Inject
  public ImageValidator(SerializableLogger logger) {
    this.logger = logger;
  }

  /**
   * Validates the uploaded image file.
   *
   * @param facesContext The FacesContext for the current request.
   * @param uiComponent The UIComponent associated with the component being validated.
   * @param part The Part representing the uploaded image file to be validated.
   * @throws ValidatorException if the validation fails.
   */
  @Override
  public void validate(FacesContext facesContext, UIComponent uiComponent, Part part) throws ValidatorException {
      if (part == null) {
          logger.info("Part is valid image as it is null.");
          return;
      }

      String mimeType = part.getContentType();
      if (!isImage(mimeType)) {
          logger.warning("The Part is not an image.");
          throw new ValidatorException(new FacesMessage("File is not a valid image."));
      }

      logger.info("Part is valid image.");
  }

    /**
     * Checks whether mimetype is image.
     *
     * @param mimeType The type.
     * @return Whether it is an image.
     */
    private boolean isImage(String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }
}
