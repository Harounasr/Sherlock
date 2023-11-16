package de.ssherlock.persistence.repository;

import de.ssherlock.control.backing.CoursesBean;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        String sqlQuery =
                """
                SELECT * FROM courses c LEFT JOIN exercises e ON c.name = e.coursename;
                """;
        List<Course>  allCourses = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String courseName = result.getString("name");
                List<Exercise> exercises = new ArrayList<>();
                do {
                    long exerciseIde = result.getLong("id");
                    String exerciseName = result.getString("name");
                    Date exercisePublishDate = result.getDate("publish_date");
                    Date exerciseRecommendedDeadline = result.getDate("recommended_deadline");
                    Date exerciseObligatoryDeadline = result.getDate("obligatory_deadline");
                    exercises.add(new Exercise(exerciseIde, exerciseName, exercisePublishDate, exerciseRecommendedDeadline, exerciseObligatoryDeadline, null));
                } while (Objects.equals(result.getString("name"), courseName) && result.next());
                allCourses.add(new Course(courseName, null, exercises));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return allCourses.stream().filter(predicate).toList();
    }
}
