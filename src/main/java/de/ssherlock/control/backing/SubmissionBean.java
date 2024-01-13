package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Submission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for submission.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class SubmissionBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service handling submission-related operations.
     */
    private final SubmissionService submissionService;

    /**
     * Service handling checker-related operations.
     */
    private final CheckerService checkerService;

    /**
     * The parent backing bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The uploaded classes of the submission in text form.
     */
    private List<List<Object[]>> files;

    /**
     * The List of checker-results.
     */
    private List<CheckerResult> checkerResults;

    /**
     * The current submission.
     */
    private Submission submission;

    /**
     * Constructor for SubmissionBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param exerciseBean      The exercise bean.
     */
    @Inject
    public SubmissionBean(
            SerializableLogger logger,
            AppSession appSession,
            SubmissionService submissionService,
            CheckerService checkerService,
            ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.exerciseBean = exerciseBean;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        submission = new Submission();
        submission.setId(exerciseBean.getSubmissionId());
        try {
            submission = submissionService.getSubmission(submission);
        } catch (BusinessNonExistentSubmissionException e) {
            logger.log(Level.INFO, "Submission not existent.");
            Notification notification = new Notification("The selected submission does not exist.", NotificationType.ERROR);
            notification.generateUIMessage();
        }
        logger.info("Submission File count" + submission.getSubmissionFiles().size());
        files = TestateBean.convertSubmissionFileToText(submission.getSubmissionFiles());
        checkerResults = checkerService.getCheckerResultsForSubmission(submission);
    }

    /**
     * Gets files.
     *
     * @return the files
     */
    public List<List<Object[]>> getFiles() {
        return files;
    }

    /**
     * Sets files.
     *
     * @param files the files
     */
    public void setFiles(List<List<Object[]>> files) {
        this.files = files;
    }

    /**
     * Gets checker results.
     *
     * @return the checker results
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Sets checker results.
     *
     * @param checkerResults the checker results
     */
    public void setCheckerResults(List<CheckerResult> checkerResults) {
        this.checkerResults = checkerResults;
    }

    /**
     * Gets submission.
     *
     * @return the submission
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Sets submission.
     *
     * @param submission the submission
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
