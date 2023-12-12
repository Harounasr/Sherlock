package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The SubmissionService class provides functionality for managing submissions and related
 * operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class SubmissionService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to SubmissionService. */
  private final SerializableLogger logger;

  /** The connection pool instance. */
  private final ConnectionPool connectionPool;

  /**
   * Constructs a SubmissionService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to SubmissionService.
   * @param connectionPool The connection pool instance.
   */
  @Inject
  public SubmissionService(SerializableLogger logger, ConnectionPool connectionPool) {
    this.logger = logger;
    this.connectionPool = connectionPool;
  }

  /**
   * Adds a new submission.
   *
   * @param submission The submission to be added.
   */
  public void addSubmission(Submission submission) {}

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
   * @param user The user for whom to retrieve submissions.
   * @param exercise The exercise for which to retrieve submissions.
   * @return A list of submissions associated with the user and exercise.
   */
  public List<Submission> getSubmissions(User user, Exercise exercise) {
    return null;
  }

  /**
   * Retrieves the newest submissions associated with the specified exercise.
   *
   * @param exercise The exercise for which to retrieve the newest submissions.
   * @return A list of the newest submissions associated with the exercise.
   */
  public List<Submission> getNewestSubmissions(Exercise exercise) {
    return null;
  }

  /**
   * Gets the submission associated with the provided id.
   *
   * @param submission The submission to retrieve.
   * @return The retrieved submission.
   * @throws BusinessNonExistentSubmissionException when the submission does not exist in the
   *     database.
   */
  public Submission getSubmission(Submission submission)
      throws BusinessNonExistentSubmissionException {
    return null;
  }
}
