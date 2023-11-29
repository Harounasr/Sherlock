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

/**
 * Implementation of CourseRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
 */
public class CourseRepositoryPsql extends RepositoryPsql implements CourseRepository {

    /**
     * Logger instance for logging messages related to CourseRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(CourseRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public CourseRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCourse(Course course) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCourse(String courseName) throws PersistenceNonExistentCourseException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean courseExists(String courseName) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getCourses() {
        String sqlQuery = "SELECT * FROM courses;";
        List<Course> allCourses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Course course = new Course();
                course.setName(result.getString("name"));
                allCourses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return allCourses;
    }
}
