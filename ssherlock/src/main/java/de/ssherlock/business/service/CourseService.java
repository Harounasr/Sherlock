package de.ssherlock.business.service;


import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import de.ssherlock.persistence.repository.CourseRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * The CourseService class provides functionality for managing courses and related operations.
 */
@Named
@Dependent
public class CourseService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private final SerializableLogger logger;

    /**
     * ConnectionPoolPsql instance for getting a database connection.
     */
    private final ConnectionPoolPsql connectionPoolPsql;

    /**
     * Constructs a CourseService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to CourseService.
     */
    @Inject
    public CourseService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }

    /**
     * Retrieves a list of all courses.
     *
     * @return A list of all courses.
     */
    public List<Course> getCourses() {
        Connection connection = connectionPoolPsql.getConnection();
        CourseRepository courseRepository = RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        List<Course> courses = courseRepository.fetchCourses((course -> true));
        connectionPoolPsql.releaseConnection(connection);
        return courses;
    }

    /**
     * Retrieves a list of courses associated with the specified user.
     *
     * @param username The user for whom to retrieve courses.
     * @return A list of courses associated with the user.
     */
    public List<Course> getCourses(String username) {
        return null;
    }

    /**
     * Retrieves a course with the specified course name.
     *
     * @param courseName The name of the course to retrieve.
     * @return The course with the specified name.
     *
     * @throws BusinessNonExistentCourseException when course does not exist in the database.
     */
    public Course getCourse(String courseName) throws BusinessNonExistentCourseException {
        Connection connection = connectionPoolPsql.getConnection();
        CourseRepository courseRepository = RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        Course course;
        try {
            course = courseRepository.fetchCourse(courseName);
        } catch (PersistenceNonExistentCourseException e) {
            throw new BusinessNonExistentCourseException(e.getMessage(), e);
        }
        connectionPoolPsql.releaseConnection(connection);
        return course;
    }

    /**
     * Adds a new course.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {

    }

    /**
     * Removes an existing course.
     *
     * @param courseName The course to remove.
     *
     * @throws BusinessNonExistentCourseException when the course does not exist in the database.
     */
    public void removeCourse(String courseName) throws BusinessNonExistentCourseException {

    }

}
