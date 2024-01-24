package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.business.service.ExerciseDescriptionImageService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Backing bean for the exerciseDescription.xhtml.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class ExerciseDescriptionBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service for handling Exercise-related actions.
     */
    private final ExerciseService exerciseService;

    /**
     * Service for handling Exercise Description Image related actions.
     */
    private final ExerciseDescriptionImageService exerciseDescriptionImageService;

    /**
     * The parent exercise bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * Whether the page is in edit mode.
     */
    private boolean editMode;

    /**
     * The uploaded image.
     */
    private transient Part uploadedImage;

    /**
     * The img component that is generated.
     */
    private String imgComponent;

    /**
     * The uploaded images.
     */
    private List<ExerciseDescriptionImage> uploadedImages;

    /**
     * Whether this exercise's recommended deadline is in the past.
     */
    private boolean recommendedDeadlinePast;

    /**
     * Whether this exercise's obligatory deadline is in the past.
     */
    private boolean obligatoryDeadlinePast;

    /**
     * Whether this exercise's publish date is in the past.
     */
    private boolean publishDatePast;

    /**
     * Whether the user can edit.
     */
    private boolean userCanEdit;


    /**
     * Constructs an ExerciseDescriptionBean.
     *
     * @param logger                          The logger used for logging within this class (Injected).
     * @param appSession                      The active session (Injected).
     * @param exerciseService                 The ExerciseService (Injected).
     * @param exerciseDescriptionImageService The ExerciseDescriptionImageService (Injected).
     * @param exerciseBean                    The parent Exercise bean (Injected).
     */
    @Inject
    public ExerciseDescriptionBean(
            SerializableLogger logger,
            AppSession appSession,
            ExerciseService exerciseService,
            ExerciseDescriptionImageService exerciseDescriptionImageService,
            ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
        this.exerciseDescriptionImageService = exerciseDescriptionImageService;
        this.exerciseBean = exerciseBean;
    }

    /**
     * Initializes the ExerciseDescriptionBean after construction. Performs any necessary setup.
     */
    @PostConstruct
    public void initialize() {
        uploadedImages = new ArrayList<>();
        exercise = new Exercise();
        exercise.setId(exerciseBean.getExerciseId());
        try {
            exercise = exerciseService.getExercise(exercise);
            logger.info("Successfully fetched exercise with id " + exercise.getId());
        } catch (BusinessNonExistentExerciseException e) {
            logger.severe("The exercise with id " + exercise.getId() + " does not exist anymore.");
            throw new RuntimeException("The exercise does not exist anymore.", e);
        }
        userCanEdit = exerciseBean.getUserCourseRole() == CourseRole.TEACHER || appSession.isAdmin();
        Calendar calendar = Calendar.getInstance();
        setRecommendedDeadlinePast(exercise.getRecommendedDeadline().toInstant().isBefore(calendar.toInstant()));
        setObligatoryDeadlinePast(exercise.getObligatoryDeadline().toInstant().isBefore(calendar.toInstant()));
        setPublishDatePast(exercise.getPublishDate().toInstant().isBefore(calendar.toInstant()));
    }

    /**
     * Switches to edit mode for the exercise details.
     */
    public void startEditMode() {
        this.editMode = true;
    }

    /**
     * Saves all changes and disables edit mode.
     */
    public void saveAndDisableEditMode() {
        this.editMode = false;
        String description = exercise.getDescription();
        for (ExerciseDescriptionImage image : uploadedImages) {
            if (description.contains(image.getUUID())) {
                exerciseDescriptionImageService.insertImage(image);
            } else {
                logger.info("Discarded image with id " + image.getUUID() + " because it was not used.");
            }
        }
        try {
            exerciseService.updateExercise(exercise);
        } catch (BusinessNonExistentExerciseException e) {
            Notification notification = new Notification(
                    "The exercise could not be updated. Please try again.", NotificationType.ERROR);
            notification.generateUIMessage();
        }
        logger.info("Exercise with id " + exercise.getId() + " was updated.");
    }

    /**
     * Uploads an image related to the exercise.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public void uploadImage() {
        if (uploadedImage == null) {
            Notification notification = new Notification("You must select an image first.", NotificationType.ERROR);
            notification.generateUIMessage();
            return;
        }
        InputStream inputStream;
        ExerciseDescriptionImage exerciseDescriptionImage = new ExerciseDescriptionImage();
        try {
            logger.fine("Start upload image.");
            inputStream = uploadedImage.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            exerciseDescriptionImage.setImage(imageBytes);
            exerciseDescriptionImage.setExerciseId(exercise.getId());
            exerciseDescriptionImage.setUUID(UUID.randomUUID().toString());
            uploadedImages.add(exerciseDescriptionImage);
        } catch (IOException e) {
            Notification notification = new Notification("The image could not be uploaded.", NotificationType.ERROR);
            notification.generateUIMessage();
        }
        imgComponent = String.format(
                "<img src='http://localhost:8080/ssherlock_war_exploded/image?id=%s'/>",
                exerciseDescriptionImage.getUUID());
        logger.info("Created img component: " + imgComponent);
    }

    /**
     * Is edit mode boolean.
     *
     * @return the boolean
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * Sets edit mode.
     *
     * @param editMode the edit mode
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    /**
     * Gets uploaded image.
     *
     * @return the uploaded image
     */
    public Part getUploadedImage() {
        return uploadedImage;
    }

    /**
     * Sets uploaded image.
     *
     * @param uploadedImage the uploaded image
     */
    public void setUploadedImage(Part uploadedImage) {
        this.uploadedImage = uploadedImage;
    }

    /**
     * Gets img component.
     *
     * @return the img component
     */
    public String getImgComponent() {
        return imgComponent;
    }

    /**
     * Sets img component.
     *
     * @param imgComponent the img component
     */
    public void setImgComponent(String imgComponent) {
        this.imgComponent = imgComponent;
    }

    /**
     * Gets exercise.
     *
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Sets exercise.
     *
     * @param exercise the exercise
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Is recommended deadline past boolean.
     *
     * @return the boolean
     */
    public boolean isRecommendedDeadlinePast() {
        return recommendedDeadlinePast;
    }

    /**
     * Sets recommended deadline past.
     *
     * @param recommendedDeadlinePast the recommended deadline past
     */
    public void setRecommendedDeadlinePast(boolean recommendedDeadlinePast) {
        this.recommendedDeadlinePast = recommendedDeadlinePast;
    }

    /**
     * Is obligatory deadline past boolean.
     *
     * @return the boolean
     */
    public boolean isObligatoryDeadlinePast() {
        return obligatoryDeadlinePast;
    }

    /**
     * Sets obligatory deadline past.
     *
     * @param obligatoryDeadlinePast the obligatory deadline past
     */
    public void setObligatoryDeadlinePast(boolean obligatoryDeadlinePast) {
        this.obligatoryDeadlinePast = obligatoryDeadlinePast;
    }

    /**
     * Is publish date past boolean.
     *
     * @return the boolean
     */
    public boolean isPublishDatePast() {
        return publishDatePast;
    }

    /**
     * Sets publish date past.
     *
     * @param publishDatePast the publish date past
     */
    public void setPublishDatePast(boolean publishDatePast) {
        this.publishDatePast = publishDatePast;
    }

    /**
     * Is user can edit boolean.
     *
     * @return the boolean
     */
    public boolean isUserCanEdit() {
        return userCanEdit;
    }

    /**
     * Sets user can edit.
     *
     * @param userCanEdit the user can edit
     */
    public void setUserCanEdit(boolean userCanEdit) {
        this.userCanEdit = userCanEdit;
    }

    public String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return simpleDateFormat.format(Date.from(Calendar.getInstance().toInstant()));
    }
}
