package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;

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
     */
    void updateChecker(Checker checker);

    /**
     * Deletes a Checker entity from the database based on its ID.
     *
     * @param id The ID of the Checker entity to be deleted.
     */
    void deleteChecker(long id);

    /**
     * Fetches a Checker entity from the database based on its ID.
     *
     * @param id The ID of the Checker entity to be fetched.
     * @return The fetched Checker entity, or null if not found.
     */
    Checker fetchChecker(long id);

    /**
     * Fetches a list of Checker entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter Checker entities.
     * @return The list of Checker entities that satisfy the predicate.
     */
    List<Checker> fetchCheckers(Predicate<Checker> predicate);
}

