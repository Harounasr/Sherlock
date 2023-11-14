package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.Checker;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CheckerListBean {

    private final Logger logger;
    private final CheckerService checkerService;
    private final AppSession appSession;

    private List<Checker> checkers;
    private String newCheckerInput;
    private String newCheckerExpectedOutput;

    @Inject
    public CheckerListBean(Logger logger, CheckerService checkerService, AppSession appSession) {
        this.logger = logger;
        this.checkerService = checkerService;
        this.appSession = appSession;
    }

    @PostConstruct
    public void initialize() {
        checkers = BackingBeanInitializationUtils.loadCheckers(null, checkerService);
    }

    /**
     * Changes the parameter for the selected Checker.
     *
     * @param e the Action Event
     */
    public void changeParameter(ActionEvent e) {

    }

    /**
     * Changes the visibility for the selected Checker.
     *
     * @param e the ActionEvent
     */
    public void changeVisibility(ActionEvent e) {

    }

    /**
     * Changes the obligation status for the selected Checker.
     *
     * @param e the ActionEvent
     */
    public void changeObligation(ActionEvent e) {

    }

    /**
     * Toggles on off for the selected non-userdefined checker.
     *
     * @param e the ActionEvent
     */
    public void toggleOnOff(ActionEvent e) {

    }

    /**
     * Adds a new User Defined Checker to the current exercise.
     */
    public void addUserDefinedChecker() {

    }

    /**
     * Removes a User Defined Checker from the current exercise.
     */
    public void removeUserDefinedChecker() {

    }

    public String getNewCheckerInput() {
        return newCheckerInput;
    }

    public void setNewCheckerInput(String newCheckerInput) {
        this.newCheckerInput = newCheckerInput;
    }

    public String getNewCheckerExpectedOutput() {
        return newCheckerExpectedOutput;
    }

    public void setNewCheckerExpectedOutput(String newCheckerExpectedOutput) {
        this.newCheckerExpectedOutput = newCheckerExpectedOutput;
    }

    public List<Checker> getCheckers() {
        return checkers;
    }

    public void setCheckers(List<Checker> checkers) {
        this.checkers = checkers;
    }
}
