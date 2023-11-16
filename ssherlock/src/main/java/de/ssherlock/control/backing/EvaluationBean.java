package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

/**
 * Backing bean for the evaluation.xhtml facelet.
 */
@Named
@RequestScoped
public class EvaluationBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service for handling Testate-related actions.
     */
    private final TestateService testateService;

    /**
     * Testate instance for evaluation.
     */
    private Testate testate;

    /**
     * Constructs an EvaluationBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param testateService The TestateService (Injected).
     */
    @Inject
    public EvaluationBean(Logger logger, AppSession appSession, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.testateService = testateService;
    }

    /**
     * Initializes the EvaluationBean after construction.
     * Retrieves the testate for evaluation.
     */
    @PostConstruct
    public void initialize() {
        testate = testateService.getTestate(null, null);
    }

    /**
     * Retrieves the testate.
     *
     * @return The testate.
     */
    public Testate getTestate() {
        return testate;
    }

}
