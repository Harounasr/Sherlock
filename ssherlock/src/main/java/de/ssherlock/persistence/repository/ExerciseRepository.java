package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for interacting with a repository of Exercise entities in the database.
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
     */
    void updateExercise(Exercise exercise);

    /**
     * Deletes an Exercise entity from the database based on its name.
     *
     * @param exerciseName The name of the Exercise entity to be deleted.
     */
    void deleteExercise(String exerciseName);

    /**
     * Fetches an Exercise entity from the database based on its name.
     *
     * @param exerciseName The name of the Exercise entity to be fetched.
     * @return The fetched Exercise entity, or null if not found.
     */
    Exercise fetchExercise(String exerciseName);

    /**
     * Fetches a list of Exercise entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter Exercise entities.
     * @return The list of Exercise entities that satisfy the predicate.
     */
    List<Exercise> fetchExercises(Predicate<Exercise> predicate);
}
