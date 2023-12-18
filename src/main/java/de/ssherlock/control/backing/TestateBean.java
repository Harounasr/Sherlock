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
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
     * The uploaded classes of the submission in text form.
     */
    private List<List<Object[]>> files;

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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        submission.setId(Integer.parseInt(requestParams.get("id")));
        try {
            submission = submissionService.getSubmission(submission);
        } catch (BusinessNonExistentSubmissionException e) {
            logger.log(Level.INFO, "Submission not existent.");
            throw new RuntimeException(e);
        }
        files = convertSubmissionFileToText(submission.getSubmissionFiles());
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
    public List<List<Object[]>> convertSubmissionFileToText(List<SubmissionFile> submissionFiles) {
        List<List<Object[]>> resultFiles = new ArrayList<>();
        for (SubmissionFile file : submissionFiles) {
            String bytesToString = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] fileContent = bytesToString.split("\n");
            List<Object[]> objects = new ArrayList<>();
            int counter = 0;
            for (String s : fileContent) {
                objects.add(new Object[]{counter, s});
                counter++;
            }
            resultFiles.add(objects);
        }
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
     * @param grades The grades.
     */
    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }
}
