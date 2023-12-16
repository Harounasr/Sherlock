package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessDBAccessException;
import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Backing bean for the allSubmissionPaginationBean.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class SubmissionPaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size for the pagination.
     */
    private static final int PAGE_SIZE = 10;

    /**
     * The {@code Logger} instance to be used in this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * The parent backing bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The Exercise service.
     */
    private final ExerciseService exerciseService;

    /**
     * Service that provides submission-based actions.
     */
    private final SubmissionService submissionService;

    /**
     * List of all submissions.
     */
    private List<Submission> submissions;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * The users role in the current course.
     */
    private CourseRole courseRole;

    /**
     * Constructs an AllSubmissionPaginationBean.
     *
     * @param logger            The logger used for logging within this class (Injected).
     * @param appSession        The active session (Injected).
     * @param submissionService The SubmissionService used for submission-related actions (Injected).
     * @param exerciseBean      The parent bean.
     * @param exerciseService   The exercise service.
     */
    @Inject
    public SubmissionPaginationBean(
            SerializableLogger logger, AppSession appSession, SubmissionService submissionService, ExerciseBean exerciseBean,
            ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.exerciseBean = exerciseBean;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the AllSubmissionPaginationBean after construction. Retrieves all submissions upon
     * creation.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setCurrentIndex(1);
        getPagination().setPageSize(6);
        exercise = new Exercise();
        exercise.setId(exerciseBean.getExerciseId());
        courseRole = exerciseBean.getUserCourseRole();
        try {
            exercise = exerciseService.getExercise(exercise);
        } catch (BusinessNonExistentExerciseException e) {
            throw new RuntimeException("The exercise does not exist anymore.", e);
        }
        loadData();
        getPagination().setLastIndex(submissions.size() - 1);
    }

    /**
     * Action to redirect the user to the selected submission.
     *
     * @param submissionId the id of the submission.
     * @return The navigation outcome.
     */
    public String selectSubmission(long submissionId) {
        return "";
    }

    /**
     * Gets submissions.
     *
     * @return the submissions
     */
    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Sets submissions.
     *
     * @param submissions the submissions
     */
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        try {
            switch (courseRole) {
            case TEACHER -> submissions = submissionService.getSubmissions(getPagination(), exercise);
            case TUTOR -> submissions = submissionService.getSubmissionsForTutor(getPagination(), appSession.getUser(), exercise);
            case MEMBER -> submissions = submissionService.getSubmissionsForStudent(getPagination(), appSession.getUser(), exercise);
            default -> submissions = Collections.emptyList();
            }
        } catch (BusinessDBAccessException | BusinessNonExistentCourseException e) {
            submissions = Collections.emptyList();
            Notification notification = new Notification("The submissions could not be loaded", NotificationType.ERROR);
            notification.generateUIMessage();
        }
    }

    /**
     * Gets course role.
     *
     * @return the course role
     */
    public CourseRole getCourseRole() {
        return courseRole;
    }

    /**
     * Sets course role.
     *
     * @param courseRole the course role
     */
    public void setCourseRole(CourseRole courseRole) {
        this.courseRole = courseRole;
    }
}
