package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.Date;
import java.util.logging.Logger;

/**
 * Backing bean for the exerciseDescription.xhtml.
 */
@Named
@RequestScoped
public class ExerciseDescriptionBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service for handling Exercise-related actions.
     */
    private final ExerciseService exerciseService;

    /**
     * The active Exercise.
     */
    private Exercise exercise;

    /**
     * The recommended deadline for the exercise.
     */
    private Date recommendedDate;

    /**
     * The mandatory deadline for the exercise.
     */
    private Date mandatoryDate;

    /**
     * The publishing date for the exercise.
     */
    private Date publishDate;

    /**
     * Constructs an ExerciseDescriptionBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     */
    @Inject
    public ExerciseDescriptionBean(Logger logger, AppSession appSession, ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the ExerciseDescriptionBean after construction.
     * Performs any necessary setup.
     */
    @PostConstruct
    public void initialize() {

    }

    /**
     * Switches to edit mode for the exercise details.
     */
    public void startEditMode() {

    }

    /**
     * Submits the changes made to the exercise details.
     */
    public void submitChanges() {

    }

    /**
     * Uploads an image related to the exercise.
     */
    public void uploadImage() {

    }

    /**
     * Gets the recommended date for the exercise.
     *
     * @return The recommended date for the exercise.
     */
    public Date getRecommendedDate() {
        return recommendedDate;
    }

    /**
     * Sets the recommended date for the exercise.
     *
     * @param recommendedDate The recommended date for the exercise.
     */
    public void setRecommendedDate(Date recommendedDate) {
        this.recommendedDate = recommendedDate;
    }

    /**
     * Gets the mandatory date for the exercise.
     *
     * @return The mandatory date for the exercise.
     */
    public Date getMandatoryDate() {
        return mandatoryDate;
    }

    /**
     * Sets the mandatory date for the exercise.
     *
     * @param mandatoryDate The mandatory date for the exercise.
     */
    public void setMandatoryDate(Date mandatoryDate) {
        this.mandatoryDate = mandatoryDate;
    }

    /**
     * Gets the publishing date for the exercise.
     *
     * @return The publishing date for the exercise.
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Sets the publishing date for the exercise.
     *
     * @param publishDate The publishing date for the exercise.
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
