package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for the exercise.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class ExerciseBean implements Serializable {

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
     * The ID of the current exercise.
     */
    private long exerciseId;

    /**
     * The ID of the current submission.
     */
    private long submissionId;

    /**
     * The target page of the content.
     */
    private String targetPage;

    /**
     * The users course role.
     */
    private CourseRole userCourseRole;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * Constructs an ExerciseBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     */
    @Inject
    public ExerciseBean(SerializableLogger logger, AppSession appSession, ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the ExerciseBean after construction. Performs any necessary setup.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        exerciseId = Long.parseLong(requestParams.get("Id"));
        logger.log(Level.INFO, "Param: " + exerciseId);
        exercise = new Exercise();
        exercise.setId(exerciseId);
        try {
            exercise = exerciseService.getExercise(exercise);
            logger.info("Successfully fetched exercise with id " + exercise.getId());
        } catch (BusinessNonExistentExerciseException e) {
            logger.severe("The exercise with id " + exercise.getId() + " does not exist anymore.");
            throw new RuntimeException("The requested exercise does not exist.", e);
        }
        User user = appSession.getUser();
        userCourseRole = user.getCourseRoles().getOrDefault(exercise.getCourseId(), CourseRole.NONE);
        if (userCourseRole == CourseRole.NONE && user.getSystemRole() != SystemRole.ADMINISTRATOR) {
            throw new NoAccessException("Can not access exercise, as not part of this course.");
        }
        this.setTargetPage("exerciseDescription.xhtml");
    }

    /**
     * Deletes the current exercise.
     *
     * @return the course page for redirection.
     */
    public String deleteExercise() {
        try {
            exerciseService.removeExercise(exercise);
            logger.log(Level.INFO, "Exercise Successfully deleted.");
            return "/view/registered/course.xhtml?faces-redirect=true&Id=" + exercise.getCourseId();
        } catch (BusinessNonExistentExerciseException e) {
            Notification notification =
                    new Notification("Exercise could not be deleted.", NotificationType.ERROR);
            notification.generateUIMessage();
            return null;
        }
    }

    /**
     * Gets target page.
     *
     * @return the target page
     */
    public String getTargetPage() {
        return targetPage;
    }

    /**
     * Sets target page.
     *
     * @param targetPage the target page
     */
    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    /**
     * Gets exercise id.
     *
     * @return the exercise id
     */
    public long getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets exercise id.
     *
     * @param exerciseId the exercise id
     */
    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets user course role.
     *
     * @return the user course role
     */
    public CourseRole getUserCourseRole() {
        return userCourseRole;
    }

    /**
     * Sets user course role.
     *
     * @param userCourseRole the user course role
     */
    public void setUserCourseRole(CourseRole userCourseRole) {
        this.userCourseRole = userCourseRole;
    }

    /**
     * Gets app session.
     *
     * @return the app session
     */
    public AppSession getAppSession() {
        return appSession;
    }

    /**
     * Gets the submission id.
     *
     * @return The submission id.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the submission id.
     *
     * @param submissionId The submission id.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }
}
