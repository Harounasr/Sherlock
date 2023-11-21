package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;

import java.util.List;
import java.util.function.Predicate;

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
     * Updates a Course entity in the database.
     *
     * @param course The Course entity to be updated.
     */
    void updateCourse(Course course);

    /**
     * Deletes a Course entity from the database based on its name.
     *
     * @param courseName The name of the Course entity to be deleted.
     */
    void deleteCourse(String courseName);

    /**
     * Fetches a Course entity from the database based on its name.
     *
     * @param courseName The name of the Course entity to be fetched.
     * @return The fetched Course entity, or null if not found.
     */
    Course fetchCourse(String courseName) throws PersistenceNonExistentCourseException;

    /**
     * Fetches a list of Course entities from the database based on a given predicate.
     *
     * @param predicate The predicate used to filter Course entities.
     * @return The list of Course entities that satisfy the predicate.
     */
    List<Course> fetchCourses(Predicate<Course> predicate);
}

