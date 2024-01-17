package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.ExerciseDescriptionImageRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.inject.spi.CDI;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * Checks if there are Images, which are not used a certain time and deletes those.
 *
 * @author Leon Höfling
 */
public class UnusedImagesCleanEvent implements Runnable {

    /** Logger instance for logging messages related to CourseService. */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(UnverifiedUsersCleanEvent.class);

    /**
     * The Connection pool for this class.
     */
    private final ConnectionPool connectionPool;

    /** Constructs a new UnusedImagesCleanEvent. */
    public UnusedImagesCleanEvent() {
        connectionPool = CDI.current().select(ConnectionPool.class).get();
    }

    /**
     * Executes the cleaning of unused images.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public void run() {
        try {
            LOGGER.info("Cleaning unused images");
            cleanUnusedImages();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error cleaning unused images", e);
        }
    }

    /**
     * Cleans unused images.
     */
    private void cleanUnusedImages() {
        Connection connection = connectionPool.getConnection();
        try {
            ExerciseDescriptionImageRepository exerciseDescriptionImageRepository =
                    RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
            exerciseDescriptionImageRepository.cleanUnusedImages();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
