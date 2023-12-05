package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.business.service.ExerciseDescriptionImageService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Backing bean for the exerciseDescription.xhtml.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class ExerciseDescriptionBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger for logging within this class. */
  private final SerializableLogger logger;

  /** The active session. */
  private final AppSession appSession;

  /** Service for handling Exercise-related actions. */
  private final ExerciseService exerciseService;

  /** Service for handling Exercise Description Image related actions. */
  private final ExerciseDescriptionImageService exerciseDescriptionImageService;

  /** The current exercise. */
  private Exercise exercise;

  /** Whether the page is in edit mode. */
  private boolean editMode;

  /** The uploaded image. */
  private Part uploadedImage;

  /** The img component that is generated. */
  private String imgComponent;

  /**
   * Constructs an ExerciseDescriptionBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
   * @param exerciseService The ExerciseService (Injected).
   * @param exerciseDescriptionImageService The ExerciseDescriptionImageService (Injected).
   */
  @Inject
  public ExerciseDescriptionBean(
      SerializableLogger logger,
      AppSession appSession,
      ExerciseService exerciseService,
      ExerciseDescriptionImageService exerciseDescriptionImageService) {
    this.logger = logger;
    this.appSession = appSession;
    this.exerciseService = exerciseService;
    this.exerciseDescriptionImageService = exerciseDescriptionImageService;
  }

  /** Initializes the ExerciseDescriptionBean after construction. Performs any necessary setup. */
  @PostConstruct
  public void initialize() {
    long exerciseId =
        (long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("exerciseId");
    logger.log(Level.INFO, "Fetched id " + exerciseId + " from flash.");
    try {
      exercise = exerciseService.getExercise(exerciseId);
    } catch (BusinessNonExistentExerciseException e) {
      throw new RuntimeException(e);
    }
  }

  /** Switches to edit mode for the exercise details. */
  public void startEditMode() {
    this.editMode = true;
  }

  /** Saves all changes and disables edit mode. */
  public void saveAndDisableEditMode() {
    this.editMode = false;
    try {
      exerciseService.updateExercise(exercise);
    } catch (BusinessNonExistentExerciseException e) {
      throw new RuntimeException(e);
    }
    logger.log(Level.INFO, "Exercise with id " + exercise.getId() + " was updated.");
  }

  /** Uploads an image related to the exercise. */
  @SuppressWarnings("checkstyle:MagicNumber")
  public void uploadImage() {
    InputStream inputStream;
    ExerciseDescriptionImage exerciseDescriptionImage = new ExerciseDescriptionImage();
    try {
      inputStream = uploadedImage.getInputStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      byte[] imageBytes = outputStream.toByteArray();
      exerciseDescriptionImage.setImage(imageBytes);
      exerciseDescriptionImage.setUUID(UUID.randomUUID().toString());
      exerciseDescriptionImageService.insertImage(exerciseDescriptionImage);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    imgComponent =
        "<img src='http://localhost:8080/ssherlock_war_exploded/image?id="
            + exerciseDescriptionImage.getUUID()
            + "'/>";
    logger.log(Level.INFO, imgComponent);
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
}
