package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;
import java.util.List;

/**
 * Interface for interacting with a repository of User entities in the database.
 *
 * @author Leon Höfling
 */
public interface UserRepository {

  /**
   * Inserts a User entity into the database.
   *
   * @param user The User entity to be inserted.
   */
  void insertUser(User user);

  /**
   * Updates a User entity in the database.
   *
   * @param user The User entity to be updated.
   * @throws PersistenceNonExistentUserException when the user does not exist in the database.
   */
  void updateUser(User user) throws PersistenceNonExistentUserException;

  /**
   * Deletes a User entity from the database based on its username.
   *
   * @param username The username of the User entity to be deleted.
   * @throws PersistenceNonExistentUserException when the user does not exist in the database.
   */
  void deleteUser(String username) throws PersistenceNonExistentUserException;

  /**
   * Gets a User entity from the database based on its username.
   *
   * @param username The username of the User entity to be fetched.
   * @return The fetched User entity, or throws PersistenceNonExistentUserException if not found.
   * @throws PersistenceNonExistentUserException If the user with the given username is not found.
   */
  User getUser(String username) throws PersistenceNonExistentUserException;

  /**
   * Gets a list of User entities from the database.
   *
   * @return The list of User entities.
   */
  List<User> getUsers();
}
