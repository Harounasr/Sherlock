package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;
import java.sql.Connection;
import java.util.List;

/**
 * Implementation of CheckerRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {

  /** Logger instance for logging messages related to CheckerRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(CheckerRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public CheckerRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertChecker(Checker checker) {}

  /** {@inheritDoc} */
  @Override
  public void updateChecker(Checker checker) throws PersistenceNonExistentCheckerException {}

  /** {@inheritDoc} */
  @Override
  public void deleteChecker(Checker checker) throws PersistenceNonExistentCheckerException {}

  /** {@inheritDoc} */
  @Override
  public Checker getChecker(Checker checker) throws PersistenceNonExistentCheckerException {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public List<Checker> getCheckers() {
    return null;
  }
}
