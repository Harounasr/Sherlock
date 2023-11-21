package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ExerciseDescriptionImageRepositoryPsql extends RepositoryPsql implements ExerciseDescriptionImageRepository{

    /**
     * {@inheritDoc}
     */
    public ExerciseDescriptionImageRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public String insertExerciseDescriptionImage(ExerciseDescriptionImage image) {
        String sqlQuery = """
            INSERT INTO exercise_description_image(uuid, image) 
            VALUES ( ?, ? );                        
        """;
        UUID uuid = UUID.randomUUID();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setObject(1, uuid);
            statement.setBytes(2, image.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return uuid.toString();
    }

    @Override
    public void updateExerciseDescriptionImage(ExerciseDescriptionImage image) {

    }

    @Override
    public void deleteExerciseDescriptionImage(String uuid) {

    }

    @Override
    public ExerciseDescriptionImage getExerciseDescriptionImage(String uuid) {
        String sqlQuery = "SELECT * FROM exercise_description_image WHERE uuid::uuid = ?;";
        ExerciseDescriptionImage image = new ExerciseDescriptionImage();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setObject(1, UUID.fromString(uuid));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                image.setUUID(uuid);
                image.setImage(result.getBytes("image"));
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
