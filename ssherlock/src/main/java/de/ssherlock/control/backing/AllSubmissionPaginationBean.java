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

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private SubmissionService submissionService;
    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;

    private List<Submission> submissions;

    public AllSubmissionPaginationBean() {

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




