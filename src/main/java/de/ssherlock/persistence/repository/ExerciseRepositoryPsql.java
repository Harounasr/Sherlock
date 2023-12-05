package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
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
 * @author Victor Vollmann
 */
public class ExerciseRepositoryPsql extends RepositoryPsql implements ExerciseRepository {
  /** Logger instance for logging messages related to ExerciseRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(ExerciseRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public ExerciseRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertExercise(Exercise exercise) {}

  /** {@inheritDoc} */
  @SuppressWarnings("checkstyle:MagicNumber")
  @Override
  public void updateExercise(Exercise exercise) throws PersistenceNonExistentExerciseException {
    String sqlQuery =
        """
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
      // TODO: update once test data is inserted in the database
      statement.setString(5, exercise.getCourseName());
      statement.setString(6, exercise.getDescription());
      statement.setLong(7, exercise.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void deleteExercise(long exerciseId) throws PersistenceNonExistentExerciseException {}

  /** {@inheritDoc} */
  @Override
  public Exercise getExercise(long exerciseId) throws PersistenceNonExistentExerciseException {
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
        throw new PersistenceNonExistentExerciseException(
            "The exercise with id " + exerciseId + "is not stored in the database");
      }
    } catch (SQLException e) {
      throw new PersistenceNonExistentExerciseException(
          "The exercise with id " + exerciseId + "is not stored in the database", e);
    }
    return exercise;
  }

  /** {@inheritDoc} */
  @Override
  public List<Exercise> getExercises(String courseName) {
    String sqlQuery =
        "SELECT * FROM courses c LEFT JOIN exercises e ON c.name = e.coursename WHERE c.name = ?;";
    List<Exercise> exercises = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
      statement.setString(1, courseName);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        do {
          Exercise exercise = new Exercise();
          exercise.setId(result.getLong("id"));
          exercise.setName(result.getString("name"));
          exercise.setPublishDate(result.getDate("publish_date"));
          exercise.setRecommendedDeadline(result.getDate("recommended_deadline"));
          exercise.setObligatoryDeadline(result.getDate("obligatory_deadline"));
          exercise.setCourseName(result.getString("coursename"));
          exercises.add(exercise);
        } while (result.next());
      }
    } catch (SQLException e) {
    }
    return exercises;
  }
}
