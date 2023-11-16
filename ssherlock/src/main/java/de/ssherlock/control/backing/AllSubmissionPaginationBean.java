package de.ssherlock.control.backing;

import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Submission;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for the allSubmissionPaginationBean.xhtml facelet.
 */
@Named
@RequestScoped
public class AllSubmissionPaginationBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides submission-based actions.
     */
    private final SubmissionService submissionService;

    /**
     * Service that provides user-based actions.
     */
    private final UserService userService;

    /**
     * List of all submissions.
     */
    private List<Submission> submissions;

    /**
     * Constructs an AllSubmissionPaginationBean.
     *
     * @param logger            The logger used for logging within this class (Injected).
     * @param appSession        The active session (Injected).
     * @param submissionService The SubmissionService used for submission-related actions (Injected).
     * @param userService       The UserService used for user-related actions (Injected).
     */
    @Inject
    public AllSubmissionPaginationBean(Logger logger, AppSession appSession, SubmissionService submissionService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.userService = userService;
    }

    /**
     * Initializes the AllSubmissionPaginationBean after construction.
     * Retrieves all submissions upon creation.
     */
    @PostConstruct
    public void initialize() {
        submissions = submissionService.getSubmissions(null); // You might want to specify parameters here
    }

    /**
     * Action to redirect the user to the selected submission.
     *
     * @param e The ActionEvent
     */
    public void selectSubmission(ActionEvent e) {
    }
}
