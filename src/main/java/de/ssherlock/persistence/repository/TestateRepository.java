package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentTestateException;
import java.util.List;

/**
 * Interface for interacting with a repository of Testate entities in the database.
 *
 * @author Victor Vollmann
 */
public interface TestateRepository {

  /**
   * Inserts a Testate entity into the database.
   *
   * @param testate The Testate entity to be inserted.
   */
  void insertTestate(Testate testate);

  /**
   * Gets a Testate entity from the database based on an exercise and a student.
   *
   * @param exercise The exercise.
   * @param student The student.
   * @return The testate.
   * @throws PersistenceNonExistentTestateException when the testate does not exist in the database.
   */
  Testate getTestate(Exercise exercise, User student)
      throws PersistenceNonExistentTestateException;

  /**
   * Gets a list of Testate entities for a specific exercise from the database.
   *
   * @param exercise The exercise.
   * @return The list of Testate entities.
   */
  List<Testate> getTestates(Exercise exercise);

  /**
   * Gets a list of Testate entities for a specific exercise and tutor from the database.
   *
   * @param exercise The exerciseId.
   * @param tutor The username of the tutor.
   * @return The list of Testate entities.
   */
  List<Testate> getTestates(Exercise exercise, User tutor);

    /**
     * Insert a new comment for the testate.
     *
     * @param testate The testate.
     */
  void insertTestateComment(Testate testate);
}
