package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import jakarta.inject.Inject;

import java.sql.Connection;

/**
 * Checks if there are Users, which have not been verified for a certain time and deletes those.
 *
 * @author Victor Vollmann
 */
public class UnverifiedUsersCleanEvent {

  /** Logger instance for logging messages related to CourseService. */
  private static final SerializableLogger LOGGER =
      LoggerCreator.get(UnverifiedUsersCleanEvent.class);

    /**
     * The Connection pool for this class.
     */
    @Inject
    private ConnectionPool connectionPool;

  /** Constructs a new UnverifiedUsersCleanEvent. */
  public UnverifiedUsersCleanEvent() {}

  /** Deletes unverified users. */
  public void cleanUnverifiedUsers() {
      Connection connection = connectionPool.getConnection();
      UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
      userRepository.deleteUnverifiedUsers();
      connectionPool.releaseConnection(connection);
  }

  /**
   * Checks if UnverifiedUsersCleanEvent is currently running.
   *
   * @return true/false according to the state of UnverifiedUsersCleanEvent.
   */
  public boolean isRunning() {
    return false;
  }

  /** Shuts down the UnverifiedUsersCleanEvent. */
  public void shutdown() {}
}
