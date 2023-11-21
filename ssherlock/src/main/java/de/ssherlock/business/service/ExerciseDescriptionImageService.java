package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.repository.ExerciseDescriptionImageRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;

@Named
@Dependent
public class ExerciseDescriptionImageService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private final SerializableLogger logger;

    private final ConnectionPoolPsql connectionPoolPsql;

    @Inject
    public ExerciseDescriptionImageService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }

    public String insertImage(ExerciseDescriptionImage image) {
        Connection connection = connectionPoolPsql.getConnection();
        ExerciseDescriptionImageRepository imageRepository = RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
        String uuid = imageRepository.insertExerciseDescriptionImage(image);
        connectionPoolPsql.releaseConnection(connection);
        return uuid;
    }

    public ExerciseDescriptionImage getImage(String uuid) {
        Connection connection = connectionPoolPsql.getConnection();
        ExerciseDescriptionImageRepository imageRepository = RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
        ExerciseDescriptionImage image;
        image = imageRepository.getExerciseDescriptionImage(uuid);
        connectionPoolPsql.releaseConnection(connection);
        return image;
    }

}
