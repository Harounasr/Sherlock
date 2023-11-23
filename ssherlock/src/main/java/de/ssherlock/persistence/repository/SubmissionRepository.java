package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Submission;
import de.ssherlock.persistence.exception.PersistenceNonExistentSubmissionException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Submission entities in the database.
 *
 * @author Victor Vollmann
 */
public interface SubmissionRepository {

    /**
     * Inserts a Submission entity into the database.
     *
     * @param submission The Submission entity to be inserted.
     */
    void insertSubmission(Submission submission);

    /**
     * Fetches a Submission entity from the database based on its ID.
     *
     * @param id The ID of the Submission entity to be fetched.
     * @return The fetched Submission entity, or null if not found.
     *
     * @throws PersistenceNonExistentSubmissionException when the submission does not exist in the database.
     */
    Submission getSubmission(long id) throws PersistenceNonExistentSubmissionException;

    /**
     * Fetches a list of Submission entities for a specific exercise from the database.
     *
     * @param exerciseId The exercise id.
     * @return The list of Submission entities of the exercise.
     */
    List<Submission> getSubmissions(long exerciseId);

}
