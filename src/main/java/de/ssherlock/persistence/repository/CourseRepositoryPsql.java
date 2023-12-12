package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementation of CourseRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
 */
public class CourseRepositoryPsql extends RepositoryPsql implements CourseRepository {

  /** Logger instance for logging messages related to CourseRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(CourseRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public CourseRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertCourse(Course course) {
    logger.log(Level.INFO, "1");
    String sqlQuery =
        """
                            INSERT INTO course
                             (course_name) VALUES (?)


                          """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, course.getName());

      int rowsAffected = statement.executeUpdate();

      if (rowsAffected == 0) {
        logger.log(Level.INFO, "not addded");
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, e.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void deleteCourse(Course course) throws PersistenceNonExistentCourseException {
    String sqlQuery = "DELETE FROM course WHERE course_name = ?";
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, course.getName());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceNonExistentCourseException();
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean courseExists(Course course) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public List<Course> getCourses() {
    String sqlQuery = "SELECT * FROM course;";
    List<Course> allCourses = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        Course course = new Course();
        course.setName(result.getString("course_name"));
        allCourses.add(course);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return allCourses;
  }
}
