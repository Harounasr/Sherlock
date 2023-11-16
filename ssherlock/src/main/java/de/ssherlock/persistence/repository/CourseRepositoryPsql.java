package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;
/**
 * Implementation of CourseRepository for PostgreSQL database.
 */
public class CourseRepositoryPsql extends RepositoryPsql implements CourseRepository {
    /**
     * Logger instance for logging messages related to CourseRepositoryPsql.
     */
    private final Logger logger = LoggerCreator.get(CourseRepositoryPsql.class);
    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public CourseRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertCourse(Course course) {

    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public void deleteCourse(String courseName) {

    }

    @Override
    public Course fetchCourse(String courseName) {
        return null;
    }

    @Override
    public List<Course> fetchCourses(Predicate<Course> predicate) {
        return null;
    }
}
