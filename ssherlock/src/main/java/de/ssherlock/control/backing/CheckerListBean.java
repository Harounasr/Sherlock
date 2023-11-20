package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Checker;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for the checkerList.xhtml facelet.
 */
@Named
@RequestScoped
public class CheckerListBean {

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
     * New Checker that can be added to the exercise.
     */
    private Checker checker;

    /**
     * List of all checkers retrieved for the exercise.
     */
    private List<Checker> checkers;

    /**
     * Constructs a CheckerListBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param checkerService  The CheckerService used for managing checkers (Injected).
     * @param checker         The Checker that will be filled by the user (Injected empty).
     */
    @Inject
    public CheckerListBean(Logger logger, AppSession appSession, CheckerService checkerService, Checker checker) {
        this.logger = logger;
        this.checkerService = checkerService;
        this.appSession = appSession;
        this.checker = checker;
    }

    /**
     * Initializes the CheckerListBean after construction.
     * Retrieves checkers for the current exercise upon creation.
     */
    @PostConstruct
    public void initialize() {
        checkers = checkerService.getCheckersForExercise(null); // You might want to specify exercise ID or parameters here
    }

    /**
     * Gets checker.
     *
     * @return the checker
     */
    public Checker getChecker() {
        return checker;
    }

    /**
     * Sets checker.
     *
     * @param checker the checker
     */
    public void setChecker(Checker checker) {
        this.checker = checker;
    }
}
