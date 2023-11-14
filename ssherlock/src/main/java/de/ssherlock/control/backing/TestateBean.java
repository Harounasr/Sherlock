package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.annotations.Pos;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TestateBean {

    private final Logger logger;
    private final AppSession appSession;
    private final UserService userService;
    private final SubmissionService submissionService;
    private final CheckerService checkerService;
    private final TestateService testateService;

    private int gradeStructure;
    private int gradeLayout;
    private int gradeReadability;
    private Map<User, CourseRole> userRoles;
    private Submission submission;
    private List<CheckerResult> checkerResults;
    private List<TestateComment> testateComments;

    @Inject
    public TestateBean(Logger logger, AppSession appSession, UserService userService, SubmissionService submissionService, CheckerService checkerService, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.testateService = testateService;
    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        toggleVisibility();
    }

    public void insertCommentInCode(ActionEvent e) {

    }

    public void insertCommentIntoTestate(ActionEvent e) {

    }

    public void gradeSubmission(ActionEvent e) {

    }

    public void rerunChecker(ActionEvent e) {

    }

    public void expandChecker(ActionEvent e) {

    }

    public void submitTestate() {

    }

    private void toggleVisibility() {

    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public int getGradeStructure() {
        return gradeStructure;
    }

    public void setGradeStructure(int gradeStructure) {
        this.gradeStructure = gradeStructure;
    }

    public int getGradeLayout() {
        return gradeLayout;
    }

    public void setGradeLayout(int gradeLayout) {
        this.gradeLayout = gradeLayout;
    }

    public int getGradeReadability() {
        return gradeReadability;
    }

    public void setGradeReadability(int gradeReadability) {
        this.gradeReadability = gradeReadability;
    }

    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    public void setCheckerResults(List<CheckerResult> checkerResults) {
        this.checkerResults = checkerResults;
    }

    public List<TestateComment> getTestateComments() {
        return testateComments;
    }

    public void setTestateComments(List<TestateComment> testateComments) {
        this.testateComments = testateComments;
    }
}
