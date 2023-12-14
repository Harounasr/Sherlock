package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ExerciseRepository for PostgreSQL database.
 *
 * @author Haroun Alswedany
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class ExerciseRepositoryPsql extends RepositoryPsql implements ExerciseRepository {
    /**
     * Logger instance for logging messages related to ExerciseRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(ExerciseRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public ExerciseRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertExercise(Exercise exercise) {
        String sqlQuery =
                """
                     INSERT INTO exercise(name, course_id, publish_date, recommended_deadline,
                         obligatory_deadline)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, exercise.getName());
            statement.setLong(2, exercise.getCourseId());
            statement.setTimestamp(3, exercise.getPublishDate());
            statement.setTimestamp(4, exercise.getRecommendedDeadline());
            statement.setTimestamp(5, exercise.getObligatoryDeadline());

            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public void updateExercise(Exercise exercise) throws PersistenceNonExistentExerciseException {
        String sqlQuery =
                """
                    UPDATE exercise
                    SET name = ?,
                        publish_date = ?,
                        recommended_deadline = ?,
                        obligatory_deadline = ?,
                        course_name = ?,
                        description = ?
                    WHERE id = ?;
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, exercise.getName());
            statement.setTimestamp(2, exercise.getPublishDate());
            statement.setTimestamp(3, exercise.getRecommendedDeadline());
            statement.setTimestamp(4, exercise.getObligatoryDeadline());
            statement.setLong(5, exercise.getCourseId());
            statement.setString(6, exercise.getDescription());
            statement.setLong(7, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceNonExistentExerciseException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteExercise(Exercise exercise) throws PersistenceNonExistentExerciseException {
        String sqlQuery = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            if (statement.executeUpdate() == 0) {
                throw new PersistenceNonExistentExerciseException(
                        "The exercise with id " + exercise.getId() + " not found");
            }
        } catch (SQLException e) {
            logger.severe("Error deleting exercise with id " + exercise.getId() + e.getMessage());
            throw new PersistenceNonExistentExerciseException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exercise getExercise(Exercise exercise) throws PersistenceNonExistentExerciseException {
        String sqlQuery = "SELECT * FROM exercise WHERE id = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                exercise.setName(result.getString("name"));
                exercise.setPublishDate(result.getTimestamp("publish_date"));
                exercise.setRecommendedDeadline(result.getTimestamp("recommended_deadline"));
                exercise.setObligatoryDeadline(result.getTimestamp("obligatory_deadline"));
                exercise.setDescription(result.getString("description"));
                exercise.setCourseId(result.getLong("course_id"));
            } else {
                throw new PersistenceNonExistentExerciseException(
                        "The exercise with id " + exercise.getId() + "is not stored in the database");
            }
        } catch (SQLException e) {
            throw new PersistenceNonExistentExerciseException(
                    "The exercise with id " + exercise.getId() + "is not stored in the database", e);
        }
        return exercise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Exercise> getExercises(Course course) {
        String sqlQuery =
                "SELECT * FROM courses c LEFT JOIN exercises e ON c.name = e.coursename WHERE c.name = ?;";
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, course.getName());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                do {
                    Exercise exercise = new Exercise();
                    exercise.setId(result.getLong("id"));
                    exercise.setName(result.getString("name"));
                    exercise.setPublishDate(result.getTimestamp("publish_date"));
                    exercise.setRecommendedDeadline(result.getTimestamp("recommended_deadline"));
                    exercise.setObligatoryDeadline(result.getTimestamp("obligatory_deadline"));
                    exercise.setCourseId(result.getLong("course_id"));
                    exercises.add(exercise);
                } while (result.next());
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving exercises: " + e.getMessage());
        }
        return exercises;
    }
}
