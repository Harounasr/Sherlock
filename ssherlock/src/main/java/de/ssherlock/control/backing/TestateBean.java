package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for testate.xhtml facelet.
 */
@Named
@RequestScoped
public class TestateBean {

    /**
     * Logger for this class.
     */
    private final Logger logger;

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
     * Grade for the structure.
     */
    private int gradeStructure;

    /**
     * Grade for the layout.
     */
    private int gradeLayout;

    /**
     * Grade for the readability.
     */
    private int gradeReadability;

    /**
     * The submission object.
     */
    private Submission submission;

    /**
     * List of checker results.
     */
    private List<CheckerResult> checkerResults;

    /**
     * List of testate comments.
     */
    private List<TestateComment> testateComments;

    /**
     * Constructor for TestateBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param testateService    The service handling testate-related operations.
     */
    @Inject
    public TestateBean(Logger logger, AppSession appSession, SubmissionService submissionService,
                       CheckerService checkerService, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.testateService = testateService;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        // Method body intentionally left empty
    }

    /**
     * Inserts a comment in the code.
     *
     * @param e The action event.
     */
    public void insertCommentInCode(ActionEvent e) {
    }

    /**
     * Inserts a comment into the testate.
     *
     * @param e The action event.
     */
    public void insertCommentIntoTestate(ActionEvent e) {
    }

    /**
     * Grades the submission.
     *
     * @param e The action event.
     */
    public void gradeSubmission(ActionEvent e) {

    }

    /**
     * Reruns a checker for the submission.
     *
     * @param e The action event.
     */
    public void rerunChecker(ActionEvent e) {

    }

    /**
     * Expands the checker to display more details.
     *
     * @param e The action event.
     */
    public void expandChecker(ActionEvent e) {

    }

    /**
     * Submits the testate.
     */
    public void submitTestate() {

    }

    /**
     * Toggles the visibility of some elements.
     */
    private void toggleVisibility() {

    }

    /**
     * Retrieves the submission.
     *
     * @return The submission object.
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Sets the submission object.
     *
     * @param submission The submission object to set.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * Retrieves the structure grade.
     *
     * @return The grade for submission structure.
     */
    public int getGradeStructure() {
        return gradeStructure;
    }

    /**
     * Sets the structure grade.
     *
     * @param gradeStructure The grade for submission structure to set.
     */
    public void setGradeStructure(int gradeStructure) {
        this.gradeStructure = gradeStructure;
    }

    /**
     * Retrieves the layout grade.
     *
     * @return The grade for submission layout.
     */
    public int getGradeLayout() {
        return gradeLayout;
    }

    /**
     * Sets the layout grade.
     *
     * @param gradeLayout The grade for submission layout to set.
     */
    public void setGradeLayout(int gradeLayout) {
        this.gradeLayout = gradeLayout;
    }

    /**
     * Retrieves the readability grade.
     *
     * @return The grade for submission readability.
     */
    public int getGradeReadability() {
        return gradeReadability;
    }

    /**
     * Sets the readability grade.
     *
     * @param gradeReadability The grade for submission readability to set.
     */
    public void setGradeReadability(int gradeReadability) {
        this.gradeReadability = gradeReadability;
    }

    /**
     * Retrieves the list of checker results.
     *
     * @return The list of checker results.
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Retrieves the list of testate comments.
     *
     * @return The list of testate comments.
     */
    public List<TestateComment> getTestateComments() {
        return testateComments;
    }

    /**
     * Sets the list of testate comments.
     *
     * @param testateComments The list of testate comments to set.
     */
    public void setTestateComments(List<TestateComment> testateComments) {
        this.testateComments = testateComments;
    }
}
