package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Pagination;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The CourseService class provides functionality for managing courses and related operations.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class CourseService implements Serializable {

    /**
     * Serial Version UID.
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
    private final ConnectionPool connectionPool;

    /**
     * Constructs a CourseService with the specified logger.
     *
     * @param logger         The logger to be used for logging messages related to CourseService.
     * @param connectionPool The connection pool.
     */
    @Inject
    public CourseService(SerializableLogger logger, ConnectionPool connectionPool) {
        this.logger = logger;
        this.connectionPool = connectionPool;
    }

    /**
     * Retrieves a list of all courses.
     *
     * @return A list of all courses.
     */
    public List<Course> getCourses() {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        List<Course> courses = courseRepository.getCourses();
        connectionPool.releaseConnection(connection);
        return courses;
    }

    /**
     * Retrieves a list of all courses.
     *
     * @param pagination The pagination.
     * @return A list of all courses.
     */
    public List<Course> getCourses(Pagination pagination) {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        List<Course> courses = courseRepository.getCourses();
        connectionPool.releaseConnection(connection);
        return sortAndFilterCourses(courses, pagination);
    }


    /**
     * Retrieves a list of courses associated with the specified user.
     *
     * @param user The user for whom to retrieve courses.
     * @return A list of courses associated with the user.
     */
    public List<Course> getCourses(User user) {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        logger.log(Level.INFO, "getting courses");
        try {
            List<Course> test;
            test = courseRepository.getCourses(user);
            logger.log(Level.INFO, test.toString());
            return test;
        } catch (PersistenceNonExistentCourseException e) {
            logger.log(Level.INFO, "service found no courses.");
        }
        connectionPool.releaseConnection(connection);
        return Collections.emptyList();
    }

    /**
     * Retrieves a list of courses associated with the specified user.
     *
     * @param pagination The pagination.
     * @param user The user for whom to retrieve courses.
     * @return A list of courses associated with the user.
     */
    public List<Course> getCourses(Pagination pagination, User user) {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        logger.log(Level.INFO, "getting courses");
        try {
            List<Course> test;
            test = courseRepository.getCourses(user);
            logger.log(Level.INFO, test.toString());
            return sortAndFilterCourses(test, pagination);
        } catch (PersistenceNonExistentCourseException e) {
            logger.log(Level.INFO, "service found no courses.");
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Collections.emptyList();
    }


    /**
     * Adds a new course.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        logger.log(Level.INFO, "adding course");
        courseRepository.insertCourse(course);
        connectionPool.releaseConnection(connection);
    }

    /**
     * Checks whether a course already exists in the database.
     *
     * @param course The course name.
     * @return true if the course exists.
     */
    public boolean courseExists(Course course) {
        return false;
    }

    /**
     * Removes an existing course.
     *
     * @param course The course to remove.
     * @throws BusinessNonExistentCourseException when the course does not exist in the database.
     */
    public void removeCourse(Course course) throws BusinessNonExistentCourseException {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        try {
            courseRepository.deleteCourse(course);
        } catch (PersistenceNonExistentCourseException e) {
            throw new BusinessNonExistentCourseException();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Gets the course associated with the id.
     *
     * @param course The course.
     * @return The course.
     *
     * @throws BusinessNonExistentCourseException when the course does not exist.
     */
    public Course getCourseById(Course course) throws BusinessNonExistentCourseException {
        Connection connection = connectionPool.getConnection();
        CourseRepository courseRepository =
                RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        try {
            course = courseRepository.getCourseById(course);
        } catch (PersistenceNonExistentCourseException e) {
            throw new BusinessNonExistentCourseException();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return course;
    }

    /**
     * Sorts and filters a list of courses.
     *
     * @param courses    The courses.
     * @param pagination The pagination.
     * @return The sorted and filtered list.
     *
     * @author Victor Vollmann
     */
    private List<Course> sortAndFilterCourses(List<Course> courses, Pagination pagination) {
        Stream<Course> courseStream = courses.stream();

        if (!pagination.getSearchString().isEmpty()) {
            courseStream = courseStream.filter(course -> course.getName().contains(pagination.getSearchString()));
        }

        String sortBy = pagination.getSortBy();
        if (!sortBy.isEmpty()) {
            Comparator<Course> comparator = switch (sortBy) {
                case "name" -> Comparator.comparing(course -> course.getName().toLowerCase());
                case "id" -> Comparator.comparing(Course::getId);
                default -> (course1, course2) -> 0;
            };
            courseStream = pagination.isSortAscending() ? courseStream.sorted(comparator) : courseStream.sorted(comparator.reversed());
        }

        return courseStream.collect(Collectors.toList());
    }
}
