package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CheckerResult;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Backing bean for the checkerResults.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class CheckerResultsBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides checker-based actions.
     */
    private final SubmissionService submissionService;

    /**
     * List of all CheckerResults.
     */
    private List<CheckerResult> checkerResults;

    /**
     * Constructs a CheckerResultsBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param submissionService The CheckerService used for managing checkers (Injected).
     */
    @Inject
    public CheckerResultsBean(SerializableLogger logger, AppSession appSession, SubmissionService submissionService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
    }

    /**
     * Initializes the CheckerResultsBean after construction.
     * Performs necessary setup actions for displaying checker results.
     */
    @PostConstruct
    public void initialize() {

    }

}
