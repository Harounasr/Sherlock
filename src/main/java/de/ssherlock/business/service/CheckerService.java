package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentCheckerException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The CheckerService class provides functionality for managing checkers and related operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class CheckerService implements Serializable {

  /** Serial Version UID */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to CheckerService. */
  private final SerializableLogger logger;

  /** Instance of the connection pool. */
  private final ConnectionPoolPsql connectionPoolPsql;

  /**
   * Constructs a CheckerService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to CheckerService.
   * @param connectionPoolPsql The connection pool.
   */
  @Inject
  public CheckerService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
    this.logger = logger;
    this.connectionPoolPsql = connectionPoolPsql;
  }

  /**
   * Adds a new checker.
   *
   * @param checker The checker to be added.
   */
  public void addChecker(Checker checker) {}

  /**
   * Removes an existing checker.
   *
   * @param checker The checker to be removed.
   */
  public void removeChecker(Checker checker) {}

  /**
   * Updates the information of an existing checker.
   *
   * @param checker The checker to be updated.
   * @throws BusinessNonExistentCheckerException when checker does not exist in the database.
   */
  public void updateChecker(Checker checker) throws BusinessNonExistentCheckerException {}

  /**
   * Retrieves a list of checkers associated with the specified exercise.
   *
   * @param exercise The exercise for which to retrieve checkers.
   * @return A list of checkers associated with the exercise.
   */
  public List<Checker> getCheckersForExercise(Exercise exercise) {
    return null;
  }
}
