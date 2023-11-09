package de.ssherlock.control.backing;

import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AllTestatesPaginationBean {
    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private UserService userService;
    @Inject
    private TestateService testateService;

    private Map<User, CourseRole> userRoles;

    private List<Testate> testates;

    public AllTestatesPaginationBean() {

    }

    /**
     * Sets up the bean.
     */
    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        testates = BackingBeanInitializationUtils.loadTestates(null, testateService);
        toggleVisibility();
    }

    /**
     * Redirects user to the selected testate.
     */
    public void selectTestate(ActionEvent e) {

    }

    private void toggleVisibility() {

    }
}
