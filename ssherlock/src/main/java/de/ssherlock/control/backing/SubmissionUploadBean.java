package de.ssherlock.control.backing;

import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
     * List of submitted files.
     */
    private List<SubmissionFile> submissionFiles;

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
        submissionFiles = unzip(archiveFile);
        logger.log(Level.INFO, "unzipped files");
        for (SubmissionFile file : submissionFiles) {
            logger.log(Level.INFO, file.getName());
        }
    }

    /**
     * Unzips the provided zip file.
     *
     * @param zipFile The file to be unzipped.
     * @return A list of byte arrays representing the contents of each ZipEntry after unzipping.
     */
    private static List<SubmissionFile> unzip(Part zipFile) {
        File tempFile;
        ZipFile actualZipFile;
        List<SubmissionFile> results = new ArrayList<>();
        try {
            InputStream inputStream = zipFile.getInputStream();
            tempFile = File.createTempFile("temp", ".zip");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            actualZipFile = new ZipFile(tempFile);
            Enumeration<? extends ZipEntry> entries = actualZipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream entryStream = actualZipFile.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = entryStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                SubmissionFile submissionFile = new SubmissionFile();
                submissionFile.setBytes(baos.toByteArray());
                submissionFile.setName(entry.getName());
                results.add(submissionFile);
            }
            actualZipFile.close();
            tempFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
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
}
