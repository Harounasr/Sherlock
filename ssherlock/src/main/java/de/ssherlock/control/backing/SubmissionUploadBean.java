package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Backing bean for the submissionUpload.xhtml facelet.
 */
@Named
@RequestScoped
public class SubmissionUploadBean {

    /**
     * Logger for this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The service handling submission-related operations.
     */
    private final SubmissionService submissionService;

    /**
     * The service handling checker-related operations.
     */
    private final CheckerService checkerService;

    /**
     * List of checkers for this exercise.
     */
    private List<Checker> checkers;

    /**
     * The current submission being processed.
     */
    private Submission currentSubmission;

    /**
     * The archive file (Part) for submitting.
     */
    private Part archiveFile;

    /**
     * Constructor for SubmissionUploadBean.
     *
     * @param logger            The logger for this class (Injected).
     * @param appSession        The active session (Injected).
     * @param submissionService The service handling submission-related operations (Injected).
     * @param checkerService    The service handling checker-related operations (Injected).
     */
    @Inject
    public SubmissionUploadBean(Logger logger, AppSession appSession, SubmissionService submissionService, CheckerService checkerService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        // (method body remains empty)
    }

    /**
     * Expands the Checker to show the entire result.
     *
     * @param e The action event triggering the expansion.
     */
    public void expandCheckerResult(ActionEvent e) {

    }

    /**
     * Upload the file.
     */
    public void submitUpload() {

    }

    /**
     * Unzips the provided zip file.
     *
     * @param zipFile The file to be unzipped.
     * @return A list of ZipEntries after unzipping.
     */
    private List<ZipEntry> unzip(Part zipFile) {
        return null;
    }

    /**
     * Runs the defined checkers on the submission.
     *
     * @return A list of CheckerResults after running checkers.
     */
    private List<CheckerResult> runCheckers() {
        return null;
    }

    /**
     * Sets the archive file for processing.
     *
     * @param archiveFile The archive file to be set.
     */
    public void setZipFile(Part archiveFile) {
        this.archiveFile = archiveFile;
    }

}
