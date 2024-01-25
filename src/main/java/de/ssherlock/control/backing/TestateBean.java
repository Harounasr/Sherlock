package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessDBAccessException;
import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.business.exception.BusinessNonExistentTestateException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Backing bean for testate.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class TestateBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service handling submission-related operations.
     */
    private final SubmissionService submissionService;

    /**
     * Service handling checker-related operations.
     */
    private final CheckerService checkerService;

    /**
     * Service handling testate-related operations.
     */
    private final TestateService testateService;

    /**
     * The parent backing bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The testate the user creates.
     */
    private Testate newTestate;

    /**
     * The possible grades.
     */
    private final List<Integer> grades;

    /**
     * The submission.
     */
    private Submission submission;

    /**
     * The uploaded classes of the submission in text form.
     */
    private List<List<Object[]>> files;

    /**
     * The List of checker-results.
     */
    private List<CheckerResult> checkerResults;

    /**
     * Boolean if the testate can be editable.
     */
    private boolean readOnly;

    /**
     * Constructor for TestateBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param testateService    The service handling testate-related operations.
     * @param exerciseBean      The exercise bean.
     */
    @SuppressWarnings("checkstyle:MagicNumber")

    @Inject
    public TestateBean(
            SerializableLogger logger,
            AppSession appSession,
            SubmissionService submissionService,
            CheckerService checkerService,
            TestateService testateService,
            ExerciseBean exerciseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.testateService = testateService;
        this.exerciseBean = exerciseBean;
        this.newTestate = new Testate();
        this.grades = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        this.submission = new Submission();
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        submission.setId(exerciseBean.getSubmissionId());
        try {
            submission = submissionService.getSubmission(submission);
        } catch (BusinessNonExistentSubmissionException e) {
            logger.log(Level.INFO, "Submission not existent.");
            throw new RuntimeException(e);
        }
        files = convertSubmissionFileToText(submission.getSubmissionFiles());
        checkerResults = checkerService.getCheckerResultsForSubmission(submission);
        readOnly = submission.isTestateCreated();
        if (submission.isTestateCreated()) {
            User user = new User();
            user.setUsername(submission.getUser());
            Exercise exercise = new Exercise();
            exercise.setId(exerciseBean.getExerciseId());
            try {
                newTestate = testateService.getTestate(exercise, user);
            } catch (BusinessNonExistentTestateException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Submits the testate.
     */
    public void submitTestate() {
        newTestate.setEvaluatorId(appSession.getUser().getId());
        newTestate.setSubmission(submission);
        try {
            testateService.addTestate(newTestate);
        } catch (BusinessDBAccessException e) {
            throw new RuntimeException(e);
        }
        exerciseBean.setTargetPage("submissionPagination.xhtml");
    }

    /**
     * Converts the current submission files to text for the facelet.
     *
     * @param submissionFiles The files.
     * @return The Text.
     */
    public static List<List<Object[]>> convertSubmissionFileToText(List<SubmissionFile> submissionFiles) {
        List<List<Object[]>> resultFiles = new ArrayList<>();
        for (SubmissionFile file : submissionFiles) {
            String bytesToString = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] fileContent = bytesToString.split("\n");
            List<Object[]> objects = new ArrayList<>();
            objects.add(new Object[]{0, file.getName()});
            int counter = 0;
            for (String s : fileContent) {
                objects.add(new Object[]{counter, s});
                counter++;
            }
            resultFiles.add(objects);
        }
        return resultFiles;
    }

    /**
     * Creates a zip archive and downloads it.
     */
    public void downloadCode() {
        try {
            String[] fileNames = new String[submission.getSubmissionFiles().size()];
            byte[][] fileContents = new byte[submission.getSubmissionFiles().size()][];
            String zipFileName = "CodeFiles.zip";
            for (int i = 0; i < submission.getSubmissionFiles().size(); i++) {
                SubmissionFile file = submission.getSubmissionFiles().get(i);
                fileNames[i] = file.getName();
                fileContents[i] = file.getBytes();
            }

            byte[] zipContent = createZipFile(fileNames, fileContents);
            downloadFile(zipFileName, zipContent);
        } catch (IOException e) {
            Notification notification = new Notification("Could not download code files.", NotificationType.ERROR);
            notification.generateUIMessage();
        }
    }

    /**
     * Creates the zip archive from the submission files.
     *
     * @param fileNames The name of each file.
     * @param filesContent The content of each file.
     * @return The zip archive.
     * @throws IOException In case the compression did not work.
     */
    private byte[] createZipFile(String[] fileNames, byte[][] filesContent) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {

            for (int i = 0; i < fileNames.length; i++) {
                ZipEntry zipEntry = new ZipEntry(fileNames[i]);
                zipOut.putNextEntry(zipEntry);

                zipOut.write(filesContent[i]);

                zipOut.closeEntry();
            }
        }
        return baos.toByteArray();
    }

    /**
     * Downloads the zip archive.
     *
     * @param fileName The name of the archive.
     * @param content The files of the archive.
     * @throws IOException In case the download did not work.
     */
    private void downloadFile(String fileName, byte[] content) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().responseReset();
        facesContext.getExternalContext().setResponseContentType("application/zip");
        facesContext.getExternalContext().setResponseContentLength(content.length);
        facesContext.getExternalContext().setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (OutputStream outputStream = facesContext.getExternalContext().getResponseOutputStream()) {
            outputStream.write(content);
        }

        facesContext.responseComplete();
    }

    /**
     * Gets testate.
     *
     * @return the testate
     */
    public Testate getNewTestate() {
        return newTestate;
    }

    /**
     * Sets testate.
     *
     * @param newTestate the testate
     */
    public void setNewTestate(Testate newTestate) {
        this.newTestate = newTestate;
    }

    /**
     * Gets the possible grades.
     *
     * @return The grades.
     */
    public List<Integer> getGrades() {
        return grades;
    }

    /**
     * Gets the files.
     *
     * @return The files.
     */
    public List<List<Object[]>> getFiles() {
        return files;
    }

    /**
     * Gets the checker results.
     *
     * @return The checker results.
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Gets the current user-role.
     *
     * @return The user-role.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the current user-role.
     *
     * @param readOnly The user-role.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}

