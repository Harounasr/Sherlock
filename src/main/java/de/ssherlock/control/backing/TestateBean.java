package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessDBAccessException;
import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.CheckerUtils;
import de.ssherlock.control.util.ZipUtils;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for testate.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class TestateBean implements Serializable {

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
     * Service handling testate-related operations.
     */
    private final TestateService testateService;

    /**
     * The parent backing bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The testate the user creates.
     */
    private Testate newTestate;

    /**
     * The possible grades.
     */
    private List<Integer> grades;

    /**
     * The submission.
     */
    private Submission submission;

    /**
     * The uploaded classes of the submission in text form.
     */
    private List<List<Object[]>> files;

    /**
     * The List of checker-results.
     */
    private List<CheckerResult> checkerResults;

    /**
     * Constructor for TestateBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param testateService    The service handling testate-related operations.
     */
    @SuppressWarnings("checkstyle:MagicNumber")

    @Inject
    public TestateBean(
            SerializableLogger logger,
            AppSession appSession,
            SubmissionService submissionService,
            CheckerService checkerService,
            TestateService testateService,
            ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.testateService = testateService;
        this.exerciseBean = exerciseBean;
        this.newTestate = new Testate();
        this.grades = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        this.submission = new Submission();
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        submission.setId(exerciseBean.getSubmissionId());
        try {
            submission = submissionService.getSubmission(submission);
        } catch (BusinessNonExistentSubmissionException e) {
            logger.log(Level.INFO, "Submission not existent.");
            throw new RuntimeException(e);
        }
        files = convertSubmissionFileToText(submission.getSubmissionFiles());
        checkerResults = checkerService.getCheckerResultsForSubmission(submission);
    }

    /**
     * Submits the testate.
     *
     * @return The page to be redirected.
     */
    public void submitTestate() {
        newTestate.setEvaluatorId(appSession.getUser().getId());
        newTestate.setSubmission(submission);
        try {
            testateService.addTestate(newTestate);
        } catch (BusinessDBAccessException e) {
            throw new RuntimeException(e);
        }
        exerciseBean.setTargetPage("submissionPagination.xhtml");
    }

    /**
     * Converts the current submission files to text for the facelet.
     *
     * @param submissionFiles The files.
     * @return The Text.
     */
    public List<List<Object[]>> convertSubmissionFileToText(List<SubmissionFile> submissionFiles) {
        List<List<Object[]>> resultFiles = new ArrayList<>();
        for (SubmissionFile file : submissionFiles) {
            String bytesToString = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] fileContent = bytesToString.split("\n");
            List<Object[]> objects = new ArrayList<>();
            int counter = 0;
            for (String s : fileContent) {
                objects.add(new Object[]{counter, s});
                counter++;
            }
            resultFiles.add(objects);
        }
        return resultFiles;
    }

    /**
     * Gets testate.
     *
     * @return the testate
     */
    public Testate getNewTestate() {
        return newTestate;
    }

    /**
     * Sets testate.
     *
     * @param newTestate the testate
     */
    public void setNewTestate(Testate newTestate) {
        this.newTestate = newTestate;
    }

    /**
     * Gets the possible grades.
     *
     * @return The grades.
     */
    public List<Integer> getGrades() {
        return grades;
    }

    /**
     * Sets the possible grades.
     *
     * @param grades The grades.
     */
    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    /**
     * Gets the files.
     *
     * @return The files.
     */
    public List<List<Object[]>> getFiles() {
        return files;
    }

    /**
     * Sets the files.
     *
     * @param files The files.
     */
    public void setFiles(List<List<Object[]>> files) {
        this.files = files;
    }

    /**
     * Gets the checker results.
     *
     * @return The checker results.
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Sets the checker results.
     *
     * @param checkerResults The checker results.
     */
    public void setCheckerResults(List<CheckerResult> checkerResults) {
        this.checkerResults = checkerResults;
    }
}
