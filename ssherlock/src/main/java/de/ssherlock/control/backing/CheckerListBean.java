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
     * List of all checkers retrieved for the exercise.
     */
    private List<Checker> checkers;

    /**
     * Entered name of the new Checker.
     */
    private String newCheckerName;

    /**
     * Entered input for the new Checker.
     */
    private String newCheckerInput;

    /**
     * Entered output for the new Checker.
     */
    private String newCheckerExpectedOutput;

    /**
     * Constructs a CheckerListBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param checkerService  The CheckerService used for managing checkers (Injected).
     */
    @Inject
    public CheckerListBean(Logger logger, AppSession appSession, CheckerService checkerService) {
        this.logger = logger;
        this.checkerService = checkerService;
        this.appSession = appSession;
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
     * Action to change the parameter for the selected Checker.
     *
     * @param e The Action Event
     */
    public void changeParameter(ActionEvent e) {
    }

    /**
     * Action to change the visibility for the selected Checker.
     *
     * @param e The ActionEvent
     */
    public void changeVisibility(ActionEvent e) {
    }

    /**
     * Setter for the entered name for the new Checker.
     *
     * @param newCheckerName The entered name.
     */
    public void setNewCheckerName(String newCheckerName) {
        this.newCheckerName = newCheckerName;
    }

    /**
     * Setter for the entered input for the new Checker.
     *
     * @param newCheckerInput The entered input.
     */
    public void setNewCheckerInput(String newCheckerInput) {
        this.newCheckerInput = newCheckerInput;
    }

    /**
     * Setter for the entered output for the new Checker.
     *
     * @param newCheckerExpectedOutput The entered output.
     */
    public void setNewCheckerExpectedOutput(String newCheckerExpectedOutput) {
        this.newCheckerExpectedOutput = newCheckerExpectedOutput;
    }
}
