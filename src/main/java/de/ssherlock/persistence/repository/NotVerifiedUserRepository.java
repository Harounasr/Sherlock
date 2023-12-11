package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;

/**
 * Interface for interacting with a repository of Not Verified Users entities in the database.
 *
 * @author Leon Höfling
 */
public interface NotVerifiedUserRepository {

  /**
   * Inserts a not verified user into the database.
   *
   * @param user The user to insert.
   */
  void insertNotVerifiedUser(User user);

  /**
   * Gets a not verified user from the database.
   *
   * @param user The user to be fetched.
   * @return the not verified User.
   *
   * @throws PersistenceNonExistentUserException when the user does not exist in the database.
   */
  User getNotVerifiedUser(User user) throws PersistenceNonExistentUserException;

  /**
   * Deletes a not verified user from the database.
   *
   * @param user The user.
   *
   * @throws PersistenceNonExistentUserException when the user does not exist in the database.
   */
  void deleteNotVerifiedUser(User user) throws PersistenceNonExistentUserException;
}
