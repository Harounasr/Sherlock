package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
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

    private final SerializableLogger logger = LoggerCreator.get(ExerciseDescriptionImageRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public ExerciseDescriptionImageRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertExerciseDescriptionImage(ExerciseDescriptionImage image) {
        logger.fine("Start query to insert image with id: " + image.getUUID() + ".");
        String sqlQuery =
                """
                    INSERT INTO exercise_image(uuid, exercise_id, exercise_image)
                    VALUES ( ?, ?, ? );
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setObject(1, UUID.fromString(image.getUUID()));
            statement.setLong(2, image.getExerciseId());
            statement.setBytes(3, image.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("The exercise description image could not be inserted into the database." + e.getMessage());
        }
        logger.fine("Successfully executed query to insert image with id: " + image.getUUID() + ".");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExerciseDescriptionImage getExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage)
            throws PersistenceNonExistentImageException {
        logger.fine("Start query to get image with id: " + exerciseDescriptionImage.getUUID() + ".");
        String sqlQuery = "SELECT * FROM exercise_image WHERE uuid::uuid = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setObject(1, UUID.fromString(exerciseDescriptionImage.getUUID()));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                exerciseDescriptionImage.setImage(result.getBytes("exercise_image"));
            } else {
                throw new PersistenceNonExistentImageException(
                        "The image with id " + exerciseDescriptionImage.getUUID() + " could not be found in the database.");
            }
        } catch (SQLException e) {
            throw new PersistenceNonExistentImageException(
                    "The image with id " + exerciseDescriptionImage.getUUID() + " could not be found in the database.", e);
        }
        logger.fine("Successfully executed query to get image with id: " + exerciseDescriptionImage.getUUID() + ".");
        return exerciseDescriptionImage;
    }
}
