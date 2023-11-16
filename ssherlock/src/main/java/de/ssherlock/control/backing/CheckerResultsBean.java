package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Backing bean for the checkerResults.xhtml facelet.
 */
@Named
@RequestScoped
public class CheckerResultsBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides checker-based actions.
     */
    private final CheckerService checkerService;

    /**
     * List of all CheckerResults.
     */
    private List<CheckerResult> checkerResults;

    /**
     * Constructs a CheckerResultsBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param checkerService The CheckerService used for managing checkers (Injected).
     */
    @Inject
    public CheckerResultsBean(Logger logger, AppSession appSession, CheckerService checkerService) {
        this.logger = logger;
        this.appSession = appSession;
        this.checkerService = checkerService;
    }

    /**
     * Initializes the CheckerResultsBean after construction.
     * Performs necessary setup actions for displaying checker results.
     */
    @PostConstruct
    public void initialize() {
        // Implementation for initialization goes here
    }

    /**
     * Action to expand a single CheckerResult.
     *
     * @param e The ActionEvent
     */
    public void expandChecker(ActionEvent e) {

    }

}
