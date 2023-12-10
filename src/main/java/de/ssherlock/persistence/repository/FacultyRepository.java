package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Faculty;
import de.ssherlock.persistence.exception.PersistenceNonExistentFacultyException;
import java.util.List;

/**
 * Interface for interacting with a repository of Faculty entities in the database.
 *
 * @author Leon Höfling
 */
public interface FacultyRepository {

  /**
   * Inserts a Faculty entity into the database.
   *
   * @param faculty The Faculty entity to be inserted.
   */
  void insertFaculty(Faculty faculty);

  /**
   * Deletes an Faculty entity from the database based on its name.
   *
   * @param facultyName The name of the Faculty entity to be deleted.
   * @throws PersistenceNonExistentExerciseException when the exercise does not exist in the
   *     database.
   */
  void deleteFaculty(String facultyName) throws PersistenceNonExistentFacultyException;

  /**
   * Fetches a list of Faculty entities from the database for a specific course.
   *
   * @return The list of Faculty entities.
   */
  List<Faculty> getFaculties();
}
