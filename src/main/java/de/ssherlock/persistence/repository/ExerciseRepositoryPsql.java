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
import java.util.logging.Level;

/**
 * Implementation of ExerciseRepository for PostgreSQL database.
 *
 * @author Haroun Alswedany
 */
@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:RegexpSingleline"})
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
                         obligatory_deadline, description)
                     VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '7 days', 
                             CURRENT_TIMESTAMP + INTERVAL '14 days', 'DEFAULTDESCRIPTION')
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, exercise.getName());
            statement.setLong(2, exercise.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error occurred while adding exercise: " + e.getMessage());
            throw new RuntimeException(e);
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
                        course_id = ?,
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
        String sqlQuery = "DELETE FROM exercise WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            if (statement.executeUpdate() == 0) {
                logger.severe("Error deleting exercise with id " + exercise.getId());
                throw new PersistenceNonExistentExerciseException();
            }
        } catch (SQLException e) {
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
                "SELECT "
                + "    c.id AS course_id, "
                + "    c.course_name, "
                + "    e.id AS exercise_id, "
                + "    e.name AS exercise_name, "
                + "    e.publish_date, "
                + "    e.recommended_deadline, "
                + "    e.obligatory_deadline, "
                + "    e.description "
                + "FROM "
                + "    course c "
                + "JOIN "
                + "    exercise e ON c.id = e.course_id "
                + "WHERE "
                + "    c.course_name = ?";
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, course.getName());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                do {
                    Exercise exercise = new Exercise();
                    exercise.setId(result.getLong("exercise_id"));
                    exercise.setName(result.getString("exercise_name"));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReminderMailSent(Exercise exercise) {
        String sqlQuery = """
                          UPDATE exercise
                          SET reminder_mail_sent = true
                          WHERE id = ?;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not update column.");
        }
    }
}
