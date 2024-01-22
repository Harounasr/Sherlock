package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentTestateException;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
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
     * Parent Backing bean for exercise.xhtml.
     */
    private final ExerciseBean exerciseBean;

    /**
     * List of all testates.
     */
    private List<Testate> testates;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * The current user.
     */
    private User user;

    /**
     * Constructs an AllTestatesPaginationBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param testateService The TestateService used for testate-related actions (Injected).
     * @param exerciseBean   The parent backing bean.
     */
    @Inject
    public AllTestatesPaginationBean(SerializableLogger logger, AppSession appSession, TestateService testateService, ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.testateService = testateService;
        this.exerciseBean = exerciseBean;
    }

    /**
     * Initializes the AllTestatesPaginationBean after construction. Retrieves all available testates upon creation.
     */
    @PostConstruct
    public void initialize() {
        exercise = new Exercise();
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setCurrentIndex(0);
        exercise.setId(exerciseBean.getExerciseId());
        user = appSession.getUser();

        if (exerciseBean.getUserCourseRole() == CourseRole.TEACHER || appSession.isAdmin()) {
            testates = testateService.getAllTestates(exercise);
        } else if (exerciseBean.getUserCourseRole() == CourseRole.TUTOR) {
            testates = testateService.getAssignedTestates(exercise, user);
        } else {
            testates = new ArrayList<>();
            try {
                testates.add(testateService.getTestate(exercise, user));
            } catch (BusinessNonExistentTestateException e) {
                logger.info("No testate found");
            }
        }
        getPagination().setLastIndex(testates.size() - 1);
    }

    /**
     * Action that redirects the user to the selected testate.
     *
     * @param testate The testate.
     * @return The navigation outcome.
     */
    public void selectTestate(Testate testate) {
        exerciseBean.setSubmissionId(testate.getSubmission().getId());
        exerciseBean.setTargetPage("testate.xhtml");
    }

    /**
     * Gets testates.
     *
     * @return the testates.
     */
    public List<Testate> getTestates() {
        return testates;
    }

    /**
     * Sets testates.
     *
     * @param testates the testates.
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
            testates = testateService.getAssignedTestates(getPagination(), exercise, user);
        }
    }

    public boolean isMember() {
        return exerciseBean.getUserCourseRole() == CourseRole.MEMBER;
    }
}
