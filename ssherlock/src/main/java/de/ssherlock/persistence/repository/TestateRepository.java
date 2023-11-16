package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Testate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Testate entities in the database.
 */
public interface TestateRepository {

    /**
     * Inserts a Testate entity into the database.
     *
     * @param testate The Testate entity to be inserted.
     */
    void insertEvaluation(Testate testate);

    /**
     * Updates a Testate entity in the database.
     *
     * @param testate The Testate entity to be updated.
     */
    void updateEvaluation(Testate testate);

    /**
     * Deletes a Testate entity from the database based on its ID.
     *
     * @param id The ID of the Testate entity to be deleted.
     */
    void deleteEvaluation(long id);

    /**
     * Fetches a Testate entity from the database based on its ID.
     *
     * @param id The ID of the Testate entity to be fetched.
     * @return The fetched Testate entity, or null if not found.
     */
    Testate fetchEvaluation(long id);

    /**
     * Fetches a list of Testate entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter Testate entities.
     * @return The list of Testate entities that satisfy the predicate.
     */
    List<Testate> fetchEvaluations(Predicate<Testate> predicate);
}

