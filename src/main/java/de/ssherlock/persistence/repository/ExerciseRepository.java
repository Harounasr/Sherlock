package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;

import java.util.List;

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
   * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the
   *     database.
   */
  void updateExercise(Exercise exercise) throws PersistenceNonExistentExerciseException;

  /**
   * Deletes an Exercise entity from the database based on its name.
   *
   * @param exercise The Exercise entity to be deleted.
   * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the
   *     database.
   */
  void deleteExercise(Exercise exercise) throws PersistenceNonExistentExerciseException;

  /**
   * Fetches an Exercise entity from the database based on its name.
   *
   * @param exercise The Exercise entity to be fetched.
   * @return The fetched Exercise entity, or null if not found.
   * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the
   *     database.
   */
  Exercise getExercise(Exercise exercise) throws PersistenceNonExistentExerciseException;

  /**
   * Fetches a list of Exercise entities from the database for a specific course.
   *
   * @param course The course.
   * @return The list of Exercise entities.
   */
  List<Exercise> getExercises(Course course);

    /**
     * Sets the reminder_mail_sent to true in the given exercise.
     *
     * @param exercise The exercise.
     */
  void updateReminderMailSent(Exercise exercise);
}
