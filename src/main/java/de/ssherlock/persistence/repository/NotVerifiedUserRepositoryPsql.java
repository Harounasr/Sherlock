package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;
import java.sql.Connection;

/**
 * Implementation of {@link NotVerifiedUserRepository} interface for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class NotVerifiedUserRepositoryPsql extends RepositoryPsql
    implements NotVerifiedUserRepository {

  /**
   * Constructs a POSTGRESQL NotVerifiedUserRepository.
   *
   * @param connection The connection to the database.
   */
  public NotVerifiedUserRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertNotVerifiedUser(User user) {}

  /** {@inheritDoc} */
  @Override
  public User getNotVerifiedUser(String token) throws PersistenceNonExistentUserException {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void deleteNotVerifiedUser(String username) throws PersistenceNonExistentUserException {}
}