package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * Checks if there are Users, which have not been verified for a certain time and deletes those.
 *
 * @author Victor Vollmann
 */
public class UnverifiedUsersCleanEvent implements Runnable {

    /** Logger instance for logging messages related to CourseService. */
    private static final SerializableLogger LOGGER =
        LoggerCreator.get(UnverifiedUsersCleanEvent.class);

    /**
     * The Connection pool for this class.
     */
    @Inject
    private ConnectionPool connectionPool;

    /** Constructs a new UnverifiedUsersCleanEvent. */
    public UnverifiedUsersCleanEvent() {}

    /**
     * Executes the cleaning of unverified users.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public void run() {
        try {
            LOGGER.info("Cleaning unverified users");
            cleanUnverifiedUsers();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error cleaning unverified users", e);
        }
    }

    /**
     *  Cleans unverified users.
     */
    private void cleanUnverifiedUsers() {
        Connection connection = connectionPool.getConnection();
        try {
            UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
            userRepository.deleteUnverifiedUsers();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
