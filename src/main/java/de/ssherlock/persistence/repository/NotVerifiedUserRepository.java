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
     * Inserts a not verified user into the database
     *
     * @param user The user to insert.
     */
    void insertNotVerifiedUser(User user);

    /**
     * Gets a not verified user from the database.
     *
     * @param token The verification token of the user.
     * @throws PersistenceNonExistentUserException when the user does not exist in the database.
     */
    User getNotVerifiedUser(String token) throws PersistenceNonExistentUserException;

    /**
     * Deletes a not verified user from the database.
     *
     * @param username The username of the user.
     * @throws PersistenceNonExistentUserException when the user does not exist in the database.
     */
    void deleteNotVerifiedUser(String username) throws PersistenceNonExistentUserException;

}
