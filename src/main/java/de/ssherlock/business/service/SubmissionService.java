package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The SubmissionService class provides functionality for managing submissions and related operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class SubmissionService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to SubmissionService.
     */
    private final SerializableLogger logger;

    /**
     * The connection pool instance.
     */
    private final ConnectionPoolPsql connectionPoolPsql;

    /**
     * Constructs a SubmissionService with the specified logger.
     *
     * @param logger             The logger to be used for logging messages related to SubmissionService.
     * @param connectionPoolPsql The connection pool instance.
     */
    @Inject
    public SubmissionService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }

    /**
     * Adds a new submission.
     *
     * @param submission The submission to be added.
     */
    public void addSubmission(Submission submission) {

    }

    /**
     * Retrieves a list of submissions associated with the specified exercise.
     *
     * @param exerciseId The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the exercise.
     */
    public List<Submission> getSubmissions(long exerciseId) {
        return null;
    }

    /**
     * Retrieves a list of submissions associated with the specified user and exercise.
     *
     * @param username          The user for whom to retrieve submissions.
     * @param exerciseId        The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the user and exercise.
     */
    public List<Submission> getSubmissions(String username, long exerciseId) {
        return null;
    }

    /**
     * Retrieves the newest submissions associated with the specified exercise.
     *
     * @param exerciseId The exercise for which to retrieve the newest submissions.
     * @return A list of the newest submissions associated with the exercise.
     */
    public List<Submission> getNewestSubmissions(long exerciseId) {
        return null;
    }

    /**
     * Gets the submission associated with the provided id.
     *
     * @param submissionId The id of the submission.
     * @return The retrieved submission.
     *
     * @throws BusinessNonExistentSubmissionException when the submission does not exist in the database.
     */
    public Submission getSubmission(long submissionId) throws BusinessNonExistentSubmissionException {
        return null;
    }
}
