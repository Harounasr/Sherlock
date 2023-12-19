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

import java.io.IOException;
import java.util.logging.Level;
import java.util.zip.ZipInputStream;

/**
 * Handles validation of uploaded archive files.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesValidator(value = "archiveValidator", managed = true)
public class ArchiveValidator implements Validator<Part> {

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

    /**
     * Maximum allowed size for the ZIP file in bytes.
     */
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * Maximum allowed number of files in the ZIP file.
     */
    private static final int MAX_FILE_COUNT = 100;


    /**
     * Default constructor.
     *
     * @param logger The logger instance.
     */
    @Inject
    public ArchiveValidator(SerializableLogger logger) {
        this.logger = logger;
    }

    /**
     * Validates the uploaded archive file.
     *
     * @param facesContext The FacesContext for the current request.
     * @param uiComponent  The UIComponent associated with the component being validated.
     * @param part         The Part representing the uploaded archive file to be validated.
     * @throws ValidatorException if the validation fails.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Part part) throws ValidatorException {
        if (part == null || part.getSize() <= 0) {
            FacesMessage facesMessage = new FacesMessage("Uploaded file is empty.");
            throw new ValidatorException(facesMessage);
        }

        if (!part.getSubmittedFileName().toLowerCase().endsWith(".zip")) {
            FacesMessage facesMessage = new FacesMessage("Invalid file format. Please upload a ZIP file.");
            throw new ValidatorException(facesMessage);
        }

        if (part.getSize() > MAX_FILE_SIZE) {
            FacesMessage facesMessage = new FacesMessage("File size exceeds the maximum allowed limit of 10 MB.");
            throw new ValidatorException(facesMessage);
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(part.getInputStream())) {
            int fileCount = 0;
            while ((zipInputStream.getNextEntry()) != null) {
                fileCount++;
                zipInputStream.closeEntry();
            }

            if (fileCount > MAX_FILE_COUNT) {
                FacesMessage facesMessage = new FacesMessage("Exceeded the maximum allowed number of files in the ZIP archive (100 files).");
                throw new ValidatorException(facesMessage);
            }
            logger.info("Validated ZIP file successfully. Files: ");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error validating archive file" + e.getMessage());
            FacesMessage facesMessage = new FacesMessage("Error validating archive file. Please try again.");
            throw new ValidatorException(facesMessage);
        }
    }
}
