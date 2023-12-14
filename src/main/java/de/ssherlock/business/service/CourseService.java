package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
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
import java.util.List;
import java.util.logging.Level;

/**
 * The CourseService class provides functionality for managing courses and related operations.
 *
 * @author Lennart Hohls
 */
@Named
@Dependent
public class CourseService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to CourseService. */
  private final SerializableLogger logger;

  /** ConnectionPoolPsql instance for getting a database connection. */
  private final ConnectionPool connectionPool;

  /** AppSession for the User. */
  private final AppSession appSession;

  /**
   * Constructs a CourseService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to CourseService.
   * @param connectionPool The connection pool.
*    @param appSession the appSession
   */
  @Inject
  public CourseService(
      SerializableLogger logger, ConnectionPool connectionPool, AppSession appSession) {
    this.logger = logger;
    this.connectionPool = connectionPool;
    this.appSession = appSession;
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
    }
    connectionPool.releaseConnection(connection);
  }
}
