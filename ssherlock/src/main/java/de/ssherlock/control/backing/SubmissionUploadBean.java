package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Named
@RequestScoped
public class SubmissionUploadBean {
    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private SubmissionService submissionService;
    @Inject
    private CheckerService checkerService;
    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;

    private List<Checker> checkers;
    private Submission currentSubmission;
    private ZipFile zipFile;

    public SubmissionUploadBean() {

    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        checkers = BackingBeanInitializationUtils.loadCheckers(null, checkerService);
    }

    public void afterUpload() {

    }

    /**
     * Expands the Checker to show the entire result.
     */
    public void expandCheckerResult(ActionEvent e) {

    }

    /**
     * Tries to submit the current submission.
     */
    public void submitUpload() {

    }

    private List<ZipEntry> unzip(ZipFile zipFile) {
        return null;
    }

    private List<CheckerResult> runCheckers() {
        return null;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }

    public void setZipFile(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public List<Checker> getCheckers() {
        return checkers;
    }

    public void setCheckers(List<Checker> checkers) {
        this.checkers = checkers;
    }

    public Submission getCurrentSubmission() {
        return currentSubmission;
    }

    public void setCurrentSubmission(Submission currentSubmission) {
        this.currentSubmission = currentSubmission;
    }

    private List<File> unZip() {
        return null;
    }
}
