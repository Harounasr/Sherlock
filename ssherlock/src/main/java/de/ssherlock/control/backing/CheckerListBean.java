package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for the checkerList.xhtml facelet.
 */
@Named
@ViewScoped
public class CheckerListBean implements Serializable {

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
    public CheckerListBean(SerializableLogger logger, AppSession appSession, CheckerService checkerService, Checker checker) {
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

    }

    /**
     * Adds the newly created checker.
     */
    public void addChecker() {

    }

    /**
     * Submits all changes.
     */
    public void submitChanges() {

    }

    /**
     * Gets checkers.
     *
     * @return the checkers
     */
    public List<Checker> getCheckers() {
        return checkers;
    }

    /**
     * Sets checkers.
     *
     * @param checkers the checkers
     */
    public void setCheckers(List<Checker> checkers) {
        this.checkers = checkers;
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
