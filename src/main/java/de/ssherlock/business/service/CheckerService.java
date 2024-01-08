package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentCheckerException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;
import de.ssherlock.persistence.repository.CheckerRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;

/**
 * The CheckerService class provides functionality for managing checkers and related operations.
 *
 * @author Lennart Hohls
 */
@Named
@Dependent
public class CheckerService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to CheckerService. */
  private final SerializableLogger logger;

  /** Instance of the connection pool. */
  private final ConnectionPool connectionPool;

  /**
   * Constructs a CheckerService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to CheckerService.
   * @param connectionPool The connection pool.
   */
  @Inject
  public CheckerService(SerializableLogger logger, ConnectionPool connectionPool) {
    this.logger = logger;
    this.connectionPool = connectionPool;
  }

  /**
   * Adds a new checker.
   *
   * @param checker The checker to be added.
   */
  public void addChecker(Checker checker) {
    Connection connection = connectionPool.getConnection();
    CheckerRepository checkerRepository =
        RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
    checkerRepository.insertChecker(checker);
    logger.log(Level.INFO, "adding checker in service");
    connectionPool.releaseConnection(connection);
  }

  /**
   * Removes an existing checker.
   *
   * @param checker The checker to be removed.
   */
  public void removeChecker(Checker checker) {
    Connection connection = connectionPool.getConnection();
    CheckerRepository checkerRepository =
        RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
    try {
      checkerRepository.deleteChecker(checker);
      logger.log(Level.INFO, "deleting checker in service");
    } catch (PersistenceNonExistentCheckerException e) {
      logger.log(Level.INFO, "service: could not delete checker");
    }
    connectionPool.releaseConnection(connection);
  }

  /**
   * Updates the information of an existing checker.
   *
   * @param checker The checker to be updated.
   * @throws BusinessNonExistentCheckerException when checker does not exist in the database.
   */
  public void updateChecker(Checker checker) throws BusinessNonExistentCheckerException {
    Connection connection = connectionPool.getConnection();
    CheckerRepository checkerRepository =
        RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
    try {
      checkerRepository.updateChecker(checker);
    } catch (PersistenceNonExistentCheckerException e) {
      logger.log(Level.INFO, "service threw this");
      throw new BusinessNonExistentCheckerException();
    }
  }

  /**
   * Retrieves a list of checkers associated with the specified exercise.
   *
   * @param exercise The exercise for which to retrieve checkers.
   * @return A list of checkers associated with the exercise.
   * @throws BusinessNonExistentCheckerException if no checkers were found.
   */
  public List<Checker> getCheckersForExercise(Exercise exercise) throws BusinessNonExistentCheckerException {
      Connection connection = connectionPool.getConnection();
      List<Checker> checkerList;
      CheckerRepository checkerRepository =
              RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
      try {
          checkerList = checkerRepository.getCheckersForExercise(exercise);
      } catch (PersistenceNonExistentCheckerException e) {
          logger.log(Level.INFO, "service threw except");
          connectionPool.releaseConnection(connection);
          throw new BusinessNonExistentCheckerException();
      }
      connectionPool.releaseConnection(connection);
      return checkerList;
  }

  /** Retrieves a list of all available Checkers.
   * @return List of Checkers
   * @throws BusinessNonExistentCheckerException if no checkers were found.*/
  public List<Checker> getChecker() throws BusinessNonExistentCheckerException {
    Connection connection = connectionPool.getConnection();
    List<Checker> checkerList;
    CheckerRepository checkerRepository =
        RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
    try {
      checkerList = checkerRepository.getCheckers();
    } catch (PersistenceNonExistentCheckerException e) {
      logger.log(Level.INFO, "service threw except");
      connectionPool.releaseConnection(connection);
      throw new BusinessNonExistentCheckerException();
    }
    connectionPool.releaseConnection(connection);
    return checkerList;
  }

    /**
     * Gets the CheckerResults for a submission.
     *
     * @param submission The submission.
     * @return The Checker Results.
     */
  public List<CheckerResult> getCheckerResultsForSubmission(Submission submission) {
      Connection connection = connectionPool.getConnection();
      CheckerRepository checkerRepository = RepositoryFactory.getCheckerRepository(RepositoryType.POSTGRESQL, connection);
      return checkerRepository.getCheckerResultsForSubmission(submission);
  }
}
