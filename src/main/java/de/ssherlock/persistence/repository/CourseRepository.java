package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import java.util.List;

/**
 * Interface for interacting with a repository of Course entities in the database.
 *
 * @author Victor Vollmann
 */
public interface CourseRepository {

  /**
   * Inserts a Course entity into the database.
   *
   * @param course The Course entity to be inserted.
   */
  void insertCourse(Course course);

  /**
   * Deletes a Course entity from the database based on its name.
   *
   * @param course The Course entity to be deleted.
   * @throws PersistenceNonExistentCourseException when the Course does not exist in the database.
   */
  void deleteCourse(Course course) throws PersistenceNonExistentCourseException;

  /**
   * Checks whether a course exists in the database.
   *
   * @param course The course.
   * @return true if the course exists.
   */
  boolean courseExists(Course course);

  /**
   * Fetches a list of Course entities from the database based on a given predicate.
   *
   * @return The list of Course entities that satisfy the predicate.
   */
  List<Course> getCourses();
}
