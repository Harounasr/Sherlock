package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.persistence.exception.PersistenceNonExistentTestateException;
import java.sql.Connection;
import java.util.List;

/**
 * Implementation of TestateRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
 */
public class TestateRepositoryPsql extends RepositoryPsql implements TestateRepository {

  /** Logger instance for logging messages related to TestateRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(TestateRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public TestateRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertTestate(Testate testate) {}

  /** {@inheritDoc} */
  @Override
  public Testate getTestate(long exerciseId, String studentUsername)
      throws PersistenceNonExistentTestateException {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public List<Testate> getTestates(long exerciseId) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public List<Testate> getTestates(long exerciseId, String tutorUsername) {
    return null;
  }
}
