package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.ExerciseDescriptionImageRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.inject.Inject;

import java.sql.Connection;

/**
 * Checks if there are Images, which are not used a certain time and deletes those.
 *
 * @author Leon Höfling
 */
public class UnusedImagesCleanEvent {

    /** Logger instance for logging messages related to CourseService. */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(UnverifiedUsersCleanEvent.class);

    /**
     * The Connection pool for this class.
     */
    @Inject
    private ConnectionPool connectionPool;

    /** Constructs a new UnusedImagesCleanEvent. */
    @Inject
    public UnusedImagesCleanEvent() {
    }

    /** Deletes unused images. */
    public void cleanUnusedImages() {
        Connection connection = connectionPool.getConnection();
        ExerciseDescriptionImageRepository exerciseDescriptionImageRepository =
                RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
        exerciseDescriptionImageRepository.cleanUnusedImages();
    }

    /**
     * Checks if UnusedImagesCleanEvent is currently running.
     *
     * @return true/false according to the state of UnusedImagesCleanEvent.
     */
    public boolean isRunning() {
        return false;
    }

    /** Shuts down the UnusedImagesCleanEvent. */
    public void shutdown() {}
}
