package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;

import java.util.List;

/**
 * Interface for interacting with a repository of Course entities in the database.
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
     * @param courseName The name of the Course entity to be deleted.
     *
     * @throws PersistenceNonExistentCourseException when the Course does not exist in the database.
     */
    void deleteCourse(String courseName) throws PersistenceNonExistentCourseException;

    /**
     * Checks whether a course exists in the database.
     *
     * @param courseName The course name.
     * @return true if the course exists.
     */
    boolean courseExists(String courseName);

    /**
     * Fetches a list of Course entities from the database based on a given predicate.
     *
     * @return The list of Course entities that satisfy the predicate.
     */
    List<Course> getCourses();
}

