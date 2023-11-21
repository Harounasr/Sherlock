package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Implementation of ExerciseRepository for PostgreSQL database.
 */
public class ExerciseRepositoryPsql extends RepositoryPsql implements ExerciseRepository {
    /**
     * Logger instance for logging messages related to ExerciseRepositoryPsql.
     */
    private final Logger logger = LoggerCreator.get(ExerciseRepositoryPsql.class);

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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateExercise(Exercise exercise) {
        String sqlQuery = """
            UPDATE exercises
            SET name = ?, 
                publish_date = ?, 
                recommended_deadline = ?,
                obligatory_deadline = ?,
                coursename = ?,
                description = ?
            WHERE id = ?;                 
        """;
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, exercise.getName());
            statement.setDate(2, exercise.getPublishDate());
            statement.setDate(3, exercise.getRecommendedDeadline());
            statement.setDate(4, exercise.getObligatoryDeadline());
            //TODO: update once test data is inserted in the database
            statement.setString(5, exercise.getCourseName());
            statement.setString(6, exercise.getDescription());
            statement.setLong(7, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteExercise(long exerciseId) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exercise fetchExercise(long exerciseId) throws PersistenceNonExistentExerciseException {
        String sqlQuery = "SELECT * FROM exercises WHERE id = ?;";
        Exercise exercise = new Exercise();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, exerciseId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                exercise.setId(result.getLong("id"));
                exercise.setName(result.getString("name"));
                exercise.setPublishDate(result.getDate("publish_date"));
                exercise.setRecommendedDeadline(result.getDate("recommended_deadline"));
                exercise.setObligatoryDeadline(result.getDate("obligatory_deadline"));
                exercise.setDescription(result.getString("description"));
                exercise.setCourseName(result.getString("coursename"));
            } else {
                throw new PersistenceNonExistentExerciseException("The exercise with id " + exerciseId + "is not stored in the database");
            }
        } catch (SQLException e) {
            throw new PersistenceNonExistentExerciseException("The exercise with id " + exerciseId + "is not stored in the database", e);
        }
        return exercise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Exercise> fetchExercises(Predicate<Exercise> predicate) {
        return null;
    }
}
