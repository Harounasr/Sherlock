package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.CourseRole;
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
public class CheckerResultsBean {

    private final Logger logger;
    private final AppSession appSession;
    private final UserService userService;
    private final SubmissionService submissionService;
    private final CheckerService checkerService;

    private List<CheckerResult> checkerResults;
    private Map<User, CourseRole> userRoles;

    @Inject
    public CheckerResultsBean(Logger logger, AppSession appSession, UserService userService, SubmissionService submissionService, CheckerService checkerService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
    }

    public void expandChecker(ActionEvent e) {

    }



}
