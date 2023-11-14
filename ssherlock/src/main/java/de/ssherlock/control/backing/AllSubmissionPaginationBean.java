package de.ssherlock.control.backing;

import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Submission;
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
public class AllSubmissionPaginationBean {

    private final Logger logger;
    private final AppSession appSession;
    private final SubmissionService submissionService;
    private final UserService userService;

    private Map<User, CourseRole> userRoles;

    private List<Submission> submissions;

    @Inject
    public AllSubmissionPaginationBean(Logger logger, AppSession appSession, SubmissionService submissionService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.userService = userService;
    }

    /**
     * Sets up the bean.
     */
    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        submissions = BackingBeanInitializationUtils.loadSubmissions(null, submissionService);
        toggleVisibility();
    }

    /**
     * Redirects user to the selected submission.
     */
    public void selectSubmission(ActionEvent e) {

    }

    private void toggleVisibility() {

    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}




