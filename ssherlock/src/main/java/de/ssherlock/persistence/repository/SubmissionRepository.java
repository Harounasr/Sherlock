package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Submission;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Submission entities in the database.
 */
public interface SubmissionRepository {

    /**
     * Inserts a Submission entity into the database.
     *
     * @param submission The Submission entity to be inserted.
     */
    void insertSubmission(Submission submission);

    /**
     * Updates a Submission entity in the database.
     *
     * @param submission The Submission entity to be updated.
     */
    void updateSubmission(Submission submission);

    /**
     * Deletes a Submission entity from the database based on its ID.
     *
     * @param id The ID of the Submission entity to be deleted.
     */
    void deleteSubmission(long id);

    /**
     * Fetches a Submission entity from the database based on its ID.
     *
     * @param id The ID of the Submission entity to be fetched.
     * @return The fetched Submission entity, or null if not found.
     */
    Submission fetchSubmission(long id);

    /**
     * Fetches a list of Submission entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter Submission entities.
     * @return The list of Submission entities that satisfy the predicate.
     */
    List<Submission> fetchSubmissions(Predicate<Submission> predicate);

    /**
     * Retrieves the maximum ID from the Submission entities in the database.
     *
     * @return The maximum ID of Submission entities, or -1 if there are no submissions.
     */
    long getMaxId();
}
