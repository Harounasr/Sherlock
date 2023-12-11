package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.exception.PersistenceNonExistentImageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Implementation of the {@link ExerciseDescriptionImageRepository} interface for POSTGRESQL.
 *
 * @author Leon Höfling
 */
public class ExerciseDescriptionImageRepositoryPsql extends RepositoryPsql
    implements ExerciseDescriptionImageRepository {

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public ExerciseDescriptionImageRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertExerciseDescriptionImage(ExerciseDescriptionImage image) {
    String sqlQuery =
        """
                              INSERT INTO exercise_description_image(uuid, image)
                              VALUES ( ?, ? );
                          """;
    UUID uuid = UUID.randomUUID();
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setObject(1, uuid);
      statement.setBytes(2, image.getImage());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public ExerciseDescriptionImage getExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage)
      throws PersistenceNonExistentImageException {
    String sqlQuery = "SELECT * FROM exercise_description_image WHERE uuid::uuid = ?;";
    ExerciseDescriptionImage image = new ExerciseDescriptionImage();
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setObject(1, UUID.fromString(exerciseDescriptionImage.getUUID()));
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        exerciseDescriptionImage.setImage(result.getBytes("image"));
      } else {
        throw new PersistenceNonExistentImageException();
      }
    } catch (SQLException e) {
      throw new PersistenceNonExistentImageException("", e);
    }
    return exerciseDescriptionImage;
  }
}
