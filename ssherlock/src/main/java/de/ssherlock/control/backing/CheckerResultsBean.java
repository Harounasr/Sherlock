package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.logging.Logger;

public class CheckerResultsBean {
    @Inject
    private Logger logger;

    @Inject
    private AppSession appSession;

    @Inject
    private UserService userService;

    @Inject
    private SubmissionService submissionService;

    @Inject
    private CheckerService checkerService;

    //private List<CheckerResults> checkerResults
    private Map<User, CourseRole> userRoles;

    public CheckerResultsBean() {

    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
    }

    public void expandChecker(ActionEvent e) {

    }

}
