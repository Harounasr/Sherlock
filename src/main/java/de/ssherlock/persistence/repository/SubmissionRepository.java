package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentSubmissionException;

import java.util.List;

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
     * @param submission The Submission entity to be fetched.
     * @return The fetched Submission entity, or null if not found.
     * @throws PersistenceNonExistentSubmissionException when the submission does not exist in the
     *                                                   database.
     */
    Submission getSubmission(Submission submission) throws PersistenceNonExistentSubmissionException;

    /**
     * Fetches a list of Submission entities for a specific exercise from the database.
     *
     * @param exercise The exercise.
     * @param students The users.
     * @return The list of Submission entities of the exercise.
     */
    List<Submission> getSubmissions(Exercise exercise, List<User> students);

    /**
     * Fetches a list of Submission entities for an exercise and a student.
     *
     * @param exercise The exercise.
     * @param user     The user.
     * @return The list of submissions.
     */
    List<Submission> getSubmissionsForStudent(Exercise exercise, User user);

    /**
     * Fetches a list of Submission entities for an exercise and a student.
     *
     * @param exercise The exercise.
     * @param user     The user.
     * @return The list of submissions.
     */
    List<Submission> getSubmissionsForTutor(Exercise exercise, User user);
}
