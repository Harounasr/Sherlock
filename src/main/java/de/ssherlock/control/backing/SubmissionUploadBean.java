package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCheckerException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.exception.ZIPNotReadableException;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.CheckerUtils;
import de.ssherlock.control.util.ZipUtils;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


/**
 * Backing bean for the submissionUpload.xhtml facelet. Handles the submission upload process.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
@SuppressWarnings("PMD.ImmutableField")
public class SubmissionUploadBean implements Serializable {

    /** Serial Version UID. */
    @Serial private static final long serialVersionUID = 1L;

    /** Logger for this class. */
    private final SerializableLogger logger;

    /** The active session. */
    private final AppSession appSession;

    /** The service handling submission-related operations. */
    private final SubmissionService submissionService;

    /** The service handling checker-related operations. */
    private final CheckerService checkerService;

    /** The current submission being processed. */
    private Submission newSubmission;

    /** List of checkers for this exercise. */
    private List<Checker> checkers;

    /** The archive file (Part) for submitting. */
    private transient Part archiveFile;

    /** List of submitted files. */
    private List<SubmissionFile> submissionFiles;

    /** Whether the user can save the submission to the database. */
    private boolean canSubmit;

    /** The checker results. */
    private  List<CheckerResult> checkerResults;

    /** The current exercise. */
    private Exercise exercise;

    /**
     * The parent exercise bean.
     */
    private ExerciseBean exerciseBean;

    /**
     * Constructor for SubmissionUploadBean.
     *
     * @param logger The logger for this class (Injected).
     * @param appSession The active session (Injected).
     * @param submissionService The service handling submission-related operations (Injected).
     * @param checkerService The service handling checker-related operations (Injected).
     */
    @Inject
    public SubmissionUploadBean(
            SerializableLogger logger,
            AppSession appSession,
            SubmissionService submissionService,
            CheckerService checkerService, ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.newSubmission = new Submission();
        checkerResults = new ArrayList<>();
        this.exerciseBean = exerciseBean;
    }

    /** Initializes the bean after construction. */
    @PostConstruct
    public void initialize() {
        exercise = new Exercise();
        exercise.setId(exerciseBean.getExerciseId());
        try {
            checkers = checkerService.getCheckersForExercise(exercise);
        } catch (BusinessNonExistentCheckerException e) {
            logger.log(Level.INFO, "submissionUpload throws this");
        }
        canSubmit = false;
    }

    /** Uploads the submission archive and runs the checkers. */
    public void upload() {
        if (archiveFile == null) {
            return;
        }

        submissionFiles = new ArrayList<>();
        try {
            submissionFiles = ZipUtils.unzipSubmissionArchive(archiveFile);
            logger.log(Level.INFO, "Unzipped files:");
            for (SubmissionFile file : submissionFiles) {
                logger.log(Level.INFO, file.getName());
            }
            CheckerUtils.runCheckers(
                    checkers, submissionFiles, appSession.getUser(), this::updateResults);
        } catch (ZIPNotReadableException e) {
            logger.log(Level.SEVERE, "Error while unzipping file: " + e.getMessage());
        }
        setCanSubmit(true);
    }

    /** Saves the created submission to the database. */
    public void submit() {
        if (isCanSubmit()) {
            newSubmission.setSubmissionFiles(submissionFiles);
            newSubmission.setUser(appSession.getUser().getUsername());
            newSubmission.setCheckerResults(checkerResults);
            newSubmission.setExerciseId(exercise.getId());
            submissionService.addSubmission(newSubmission);
            canSubmit = false;
        }
    }

    /**
     * Sets the archive file for processing.
     *
     * @param archiveFile The archive file to be set.
     */
    public void setArchiveFile(Part archiveFile) {
        this.archiveFile = archiveFile;
    }

    /**
     * Gets archive file.
     *
     * @return the archive file
     */
    public Part getArchiveFile() {
        return archiveFile;
    }

    /**
     * Gets submission files.
     *
     * @return the submission files
     */
    public List<SubmissionFile> getSubmissionFiles() {
        return submissionFiles;
    }

    /**
     * Sets submission files.
     *
     * @param submissionFiles the submission files
     */
    public void setSubmissionFiles(List<SubmissionFile> submissionFiles) {
        this.submissionFiles = submissionFiles;
    }

    /**
     * Is can submit boolean.
     *
     * @return the boolean
     */
    public boolean isCanSubmit() {
        if (checkerResults.isEmpty()) {
            return false;
        } else {
            for (CheckerResult checkerResult : checkerResults) {
                if (checkerResult.getChecker().isMandatory() && !checkerResult.isPassed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets can submit.
     *
     * @param canSubmit the can submit
     */
    public void setCanSubmit(boolean canSubmit) {
        this.canSubmit = canSubmit;
    }

    /**
     * Updates the checker results.
     *
     * @param result The result to add.
     */
    private void updateResults(CheckerResult result) {
        synchronized (checkerResults) {
            checkerResults.add(result);
        }
    }

    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }
}
