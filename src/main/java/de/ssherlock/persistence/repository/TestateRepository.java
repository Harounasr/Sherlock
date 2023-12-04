package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Testate;
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
   * Gets a Testate entity from the database based on an exercise and a student's username.
   *
   * @param exerciseId The exercise ID.
   * @param studentUsername The student's username.
   * @return The testate.
   * @throws PersistenceNonExistentTestateException when the testate does not exist in the database.
   */
  Testate getTestate(long exerciseId, String studentUsername)
      throws PersistenceNonExistentTestateException;

  /**
   * Gets a list of Testate entities for a specific exercise from the database.
   *
   * @param exerciseId The exerciseId.
   * @return The list of Testate entities.
   */
  List<Testate> getTestates(long exerciseId);

  /**
   * Gets a list of Testate entities for a specific exercise and tutor from the database.
   *
   * @param exerciseId The exerciseId.
   * @param tutorUsername The username of the tutor.
   * @return The list of Testate entities.
   */
  List<Testate> getTestates(long exerciseId, String tutorUsername);
}
