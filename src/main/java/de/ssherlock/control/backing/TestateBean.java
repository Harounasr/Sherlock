package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.ObserverException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for testate.xhtml facelet.
 *
 * @author Victor Vollmann
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
     * The testate the user creates.
     */
    private Testate newTestate;

    /**
     * The possible grades.
     */
    private List<Integer> grades;

    /**
     * The submission.
     */
    private Submission submission;

    /**
     * Constructor for TestateBean.
     *
     * @param logger            The logger for this class.
     * @param appSession        The active session.
     * @param submissionService The service handling submission-related operations.
     * @param checkerService    The service handling checker-related operations.
     * @param testateService    The service handling testate-related operations.
     */
    @Inject
    public TestateBean(
            SerializableLogger logger,
            AppSession appSession,
            SubmissionService submissionService,
            CheckerService checkerService,
            TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.checkerService = checkerService;
        this.testateService = testateService;
        this.newTestate = new Testate();
        this.grades = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        this.submission = new Submission();
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {

        submission.setId();
        try {
            submission = submissionService.getSubmission(submission);
        } catch (BusinessNonExistentSubmissionException e) {
            logger.log(Level.INFO, "Submission not existent.");
            throw new RuntimeException(e);
        }

    }

    /**
     * Reruns a checker for the submission.
     *
     * @param checkerResult Result of the checker to rerun.
     */
    public void rerunChecker(CheckerResult checkerResult) {}

    /**
     * Submits the testate.
     */
    public void submitTestate() {
        testateService.addTestate(newTestate);
    }

    /**
     * Converts the current submission files to text for the facelet.
     *
     * @param submissionFiles The files.
     * @return The Text.
     */
    public List<Object[]> convertSubmissionFileToText(List<SubmissionFile> submissionFiles) {
        List<Object[]> resultFiles;
        for (SubmissionFile file : submissionFiles) {
            file.getBytes()
        }
    /*
    List<Object[]> fileContentLines = new ArrayList<>();
    try {
        if (uploadedFile != null) {
            try (InputStream input = uploadedFile.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                StringBuilder content = new StringBuilder();

                String line;
                int counter = 0;
                while ((line = reader.readLine()) != null) {
                    counter++;
                    content.append(line).append("\n");
                    fileContentLines.add(new Object[] {counter, line}); // Add this line
                }

                fileContent = content.toString();
            }
        }
    } catch (Exception e) {
        logger.log(Level.INFO, "Inside upload method");
        e.printStackTrace();
    }
     */
        return null;
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
     * Sets the possible grades.
     *
     * @param grades
     */
    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }
}
