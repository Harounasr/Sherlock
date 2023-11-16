package de.ssherlock.business.service;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * The SubmissionService class provides functionality for managing submissions and related operations.
 */
@Named
@Dependent
public class SubmissionService {

    /**
     * Logger instance for logging messages related to SubmissionService.
     */
    private final Logger logger;

    /**
     * Constructs a SubmissionService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to SubmissionService.
     */
    @Inject
    public SubmissionService(Logger logger) {
        this.logger = logger;
    }

    /**
     * Adds a new submission.
     *
     * @param submission The submission to be added.
     */
    public void addSubmission(Submission submission) {

    }

    /**
     * Removes an existing submission.
     */
    public void removeSubmission() {

    }

    /**
     * Retrieves a list of submissions associated with the specified exercise.
     *
     * @param exercise The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the exercise.
     */
    public List<Submission> getSubmissions(Exercise exercise) {
        return null;
    }

    /**
     * Retrieves a list of submissions associated with the specified user and exercise.
     *
     * @param user     The user for whom to retrieve submissions.
     * @param exercise The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the user and exercise.
     */
    public List<Submission> getSubmissions(User user, Exercise exercise) {
        return null;
    }

    /**
     * Updates the information of an existing submission.
     *
     * @param submission The submission to be updated.
     */
    public void updateSubmission(Submission submission) {

    }

    /**
     * Retrieves the newest submissions associated with the specified exercise.
     *
     * @param exercise The exercise for which to retrieve the newest submissions.
     * @return A list of the newest submissions associated with the exercise.
     */
    public List<Submission> getNewestSubmission(Exercise exercise) {
        return null;
    }
}
