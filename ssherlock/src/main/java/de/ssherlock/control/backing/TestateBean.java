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
     * The testate the user creates.
     */
    private Testate testate;

    /**
     * Constructor for TestateBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param testateService    The service handling testate-related operations.
     * @param testate           The testate the user creates (Injected empty).
     */
    @Inject
    public TestateBean(Logger logger, AppSession appSession, SubmissionService submissionService,
                       CheckerService checkerService, TestateService testateService, Testate testate) {
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
     * Gets testate.
     *
     * @return the testate
     */
    public Testate getTestate() {
        return testate;
    }

    /**
     * Sets testate.
     *
     * @param testate the testate
     */
    public void setTestate(Testate testate) {
        this.testate = testate;
    }
}
