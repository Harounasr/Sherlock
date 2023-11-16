package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * Backing Bean for the allTestatesPagination.xhtml facelet.
 */
@Named
@RequestScoped
public class AllTestatesPaginationBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides user-based actions.
     */
    private final UserService userService;

    /**
     * Service that provides testate-based actions.
     */
    private final TestateService testateService;

    /**
     * List of all testates.
     */
    private List<Testate> testates;

    /**
     * Constructs an AllTestatesPaginationBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param userService     The UserService used for user-related actions (Injected).
     * @param testateService  The TestateService used for testate-related actions (Injected).
     */
    @Inject
    public AllTestatesPaginationBean(Logger logger, AppSession appSession, UserService userService, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.testateService = testateService;
    }

    /**
     * Initializes the AllTestatesPaginationBean after construction.
     * Retrieves all available testates upon creation.
     */
    @PostConstruct
    public void initialize() {
        testates = testateService.getTestates(null); // You might want to specify parameters here
    }

    /**
     * Action that redirects the user to the selected testate.
     *
     * @param e The ActionEvent
     */
    public void selectTestate(ActionEvent e) {
        // Implementation to handle testate selection and navigation goes here
    }
}
