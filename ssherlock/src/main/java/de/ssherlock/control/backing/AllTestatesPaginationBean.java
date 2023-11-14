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
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AllTestatesPaginationBean {

    private final Logger logger;
    private final AppSession appSession;
    private final UserService userService;
    private final TestateService testateService;

    private Map<User, CourseRole> userRoles;

    private List<Testate> testates;

    @Inject
    public AllTestatesPaginationBean(Logger logger, AppSession appSession, UserService userService, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.testateService = testateService;
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
