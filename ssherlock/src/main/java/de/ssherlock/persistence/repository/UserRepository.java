package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of User entities in the database.
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
     */
    void updateUser(User user);

    /**
     * Deletes a User entity from the database based on its username.
     *
     * @param username The username of the User entity to be deleted.
     */
    void deleteUser(String username);

    /**
     * Fetches a User entity from the database based on its username.
     *
     * @param username The username of the User entity to be fetched.
     * @return The fetched User entity, or throws PersistenceNonExistentUserException if not found.
     * @throws PersistenceNonExistentUserException If the user with the given username is not found.
     */
    User fetchUser(String username) throws PersistenceNonExistentUserException;

    /**
     * Fetches a list of User entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter User entities.
     * @return The list of User entities that satisfy the predicate.
     */
    List<User> fetchUsers(Predicate<User> predicate);
}

