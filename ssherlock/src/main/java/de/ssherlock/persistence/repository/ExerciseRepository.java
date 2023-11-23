package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Exercise entities in the database.
 *
 * @author Victor Vollmann
 */
public interface ExerciseRepository {

    /**
     * Inserts an Exercise entity into the database.
     *
     * @param exercise The Exercise entity to be inserted.
     */
    void insertExercise(Exercise exercise);

    /**
     * Updates an Exercise entity in the database.
     *
     * @param exercise The Exercise entity to be updated.
     *
     * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the database.
     */
    void updateExercise(Exercise exercise) throws PersistenceNonExistentExerciseException;

    /**
     * Deletes an Exercise entity from the database based on its name.
     *
     * @param exerciseId The name of the Exercise entity to be deleted.
     *
     * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the database.
     */
    void deleteExercise(long exerciseId) throws PersistenceNonExistentExerciseException;

    /**
     * Fetches an Exercise entity from the database based on its name.
     *
     * @param exerciseId The name of the Exercise entity to be fetched.
     * @return The fetched Exercise entity, or null if not found.
     *
     * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the database.
     */
    Exercise getExercise(long exerciseId) throws PersistenceNonExistentExerciseException;

    /**
     * Fetches a list of Exercise entities from the database for a specific course.
     *
     * @param courseName The course.
     *
     * @return The list of Exercise entities.
     */
    List<Exercise> getExercises(String courseName);
}
