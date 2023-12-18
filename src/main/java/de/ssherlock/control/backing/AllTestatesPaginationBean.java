package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    /** The current user. */
    private User user;

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
        getPagination().setPageSize(PAGE_SIZE);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        exercise.setId(Long.parseLong(requestParams.get("Id")));
        user = appSession.getUser();

        if (user.getSystemRole() == SystemRole.TEACHER || appSession.isAdmin()) {
            testates = testateService.getAllTestates(getPagination(), exercise);
        } else {
            testateService.getAssignedTestates(getPagination(), exercise, user);
        }
        getPagination().setLastIndex(testates.size() - 1);
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
        if (user.getSystemRole() == SystemRole.TEACHER || appSession.isAdmin()) {
            testates = testateService.getAllTestates(getPagination(), exercise);
        } else {
            testateService.getAssignedTestates(getPagination(), exercise,user);
        }
    }

}
