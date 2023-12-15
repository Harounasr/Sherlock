package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Backing Bean for the allTestatesPagination.xhtml facelet.
 *
 * @author Haroun Alswedany
 */
@Named
@ViewScoped
public class AllTestatesPaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size for the pagination.
     */
    private static final int PAGE_SIZE = 10;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides testate-based actions.
     */
    private final TestateService testateService;

    /**
     * List of all testates.
     */
    private List<Testate> testates;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * Constructs an AllTestatesPaginationBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param testateService The TestateService used for testate-related actions (Injected).
     */
    @Inject
    public AllTestatesPaginationBean(
            SerializableLogger logger, AppSession appSession, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.testateService = testateService;
    }

    /**
     * Initializes the AllTestatesPaginationBean after construction. Retrieves all available testates upon creation.
     */
    @PostConstruct
    public void initialize() {
        exercise = new Exercise();
        exercise.setId(0L);
        loadData();
    }

    /**
     * Action that redirects the user to the selected testate.
     *
     * @param exerciseId The exercise id.
     * @param username   The username.
     * @return The navigation outcome.
     */
    public String selectTestate(long exerciseId, String username) {
        return "";
    }

    /**
     * Gets testates.
     *
     * @return the testates
     */
    public List<Testate> getTestates() {
        return testates;
    }

    /**
     * Sets testates.
     *
     * @param testates the testates
     */
    public void setTestates(List<Testate> testates) {
        this.testates = testates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        testateService.getAllTestates(exercise);
    }

}
