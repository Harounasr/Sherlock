package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentTestateException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.TestateRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * The TestateService class provides functionality for managing testates and related operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class TestateService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to TestateService. */
  private final SerializableLogger logger;

  /** The connection pool instance. */
  private final ConnectionPool connectionPool;

  /**
   * Constructs a TestateService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to TestateService.
   * @param connectionPool The connection pool instance.
   */
  @Inject
  public TestateService(SerializableLogger logger, ConnectionPool connectionPool) {
    this.logger = logger;
    this.connectionPool = connectionPool;
  }

  /**
   * Retrieves a list of assigned testates associated with the specified exercise and user.
   *
   * @param exercise The exercise for which to retrieve assigned testates.
   * @param user The user for whom to retrieve assigned testates.
   * @return A list of assigned testates associated with the exercise and user.
   */
  public List<Testate> getAssignedTestates(Exercise exercise, User user) {
    Connection connection = connectionPool.getConnection();
    TestateRepository testateRepository =
        RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
    List<Testate> testate = testateRepository.getTestates(exercise, user);
    connectionPool.releaseConnection(connection);

    return testate;
  }

  /**
   * Retrieves a list of all testates associated with the specified exercise.
   *
   * @param exercise The exercise for which to retrieve the testates.
   * @return A list of assigned testates associated with the exercise and user.
   */
  public List<Testate> getAllTestates(Exercise exercise) {
    Connection connection = connectionPool.getConnection();
    TestateRepository testateRepository =
        RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
    List<Testate> testates = testateRepository.getTestates(exercise);
    connectionPool.releaseConnection(connection);
    return testates;
  }

  /**
   * Retrieves a testate associated with the specified exercise and user.
   *
   * @param exercise The exercise for which to retrieve the testate.
   * @param user The user for whom to retrieve the testate.
   * @return The testate associated with the exercise and user.
   * @throws BusinessNonExistentTestateException when the testate does not exist in the database.
   */
  public Testate getTestate(Exercise exercise, User user)
      throws BusinessNonExistentTestateException {
    return null;
  }

  /**
   * Updates the information of an existing testate.
   *
   * @param testate The testate to be updated.
   */
  public void addTestate(Testate testate) {}
}
