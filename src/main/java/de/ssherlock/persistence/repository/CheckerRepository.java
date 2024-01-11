package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;

import java.util.List;

/**
 * Interface for interacting with a repository of Checker entities in the database.
 *
 * @author Leon Höfling
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
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    void updateChecker(Checker checker) throws PersistenceNonExistentCheckerException;

    /**
     * Deletes a Checker entity from the database based on its ID.
     *
     * @param checker The Checker entity to be deleted.
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    void deleteChecker(Checker checker) throws PersistenceNonExistentCheckerException;

    /**
     * Gets a Checker entity from the database based on its ID.
     *
     * @param checker The Checker entity to be fetched.
     * @return The fetched Checker entity, or null if not found.
     * @throws PersistenceNonExistentCheckerException when the Checker does not exist in the database.
     */
    Checker getChecker(Checker checker) throws PersistenceNonExistentCheckerException;

    /**
     * Gets a list of Checker entities from the database based on a given predicate.
     *
     * @return The list of Checker entities that satisfy the predicate.
     * @throws PersistenceNonExistentCheckerException if no checkers were found.
     */
    List<Checker> getCheckers() throws PersistenceNonExistentCheckerException;

    /**
     * Gets a list of Checker entities based on an exercise id.
     *
     * @param exercise the exercise
     * @return List of Checker
     * @throws PersistenceNonExistentCheckerException if no checkers were found.
     */
    List<Checker> getCheckersForExercise(Exercise exercise) throws PersistenceNonExistentCheckerException;

    /**
     * Gets a list of checker results based on a submission.
     *
     * @param submission The submission.
     * @return The checker results.
     */
    List<CheckerResult> getCheckerResultsForSubmission(Submission submission);
}
