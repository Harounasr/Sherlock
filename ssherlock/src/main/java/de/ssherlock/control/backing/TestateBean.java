package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
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
import org.jboss.logging.annotations.Pos;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TestateBean {

    //TODO DTO für Kommentare
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

    @Inject
    private TestateService testateService;

    private int gradeStructure;
    private int gradeLayout;
    private int gradeReadability;

    private Map<User, CourseRole> userRoles;
    private Submission submission;
    //private List<CheckerResult> checkerResults;
    //private List<TestateComment> testateComments;

    public TestateBean() {

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
}
