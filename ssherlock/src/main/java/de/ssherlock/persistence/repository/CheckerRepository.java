package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Checker entities in the database.
 */
public interface CheckerRepository {

    /**
     * Inserts a Checker entity into the database.
     *
     * @param checker The Checker entity to be inserted.
     */
    void insertChecker(Checker checker);

    /**
     * Updates a Checker entity in the database.
     *
     * @param checker The Checker entity to be updated.
     *
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    void updateChecker(Checker checker) throws PersistenceNonExistentCheckerException;

    /**
     * Deletes a Checker entity from the database based on its ID.
     *
     * @param id The ID of the Checker entity to be deleted.
     *
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    void deleteChecker(long id) throws PersistenceNonExistentCheckerException;

    /**
     * Gets a Checker entity from the database based on its ID.
     *
     * @param id The ID of the Checker entity to be fetched.
     * @return The fetched Checker entity, or null if not found.
     *
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    Checker getChecker(long id) throws PersistenceNonExistentCheckerException;

    /**
     * Gets a list of Checker entities from the database based on a given predicate.
     *
     * @return The list of Checker entities that satisfy the predicate.
     */
    List<Checker> getCheckers();
}

