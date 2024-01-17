package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import jakarta.enterprise.inject.spi.CDI;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * Resets the Password Attempts for every user every hour.
 *
 * @author Lennart Hohls
 */
public class ResetPasswordAttemptsEvent implements Runnable {

    /**
     * The Connection pool for this class.
     */
    private static final SerializableLogger LOGGER = LoggerCreator.get(ResetPasswordAttemptsEvent.class);

    /**
     * The Connection Pool.
     */
    private final ConnectionPool connectionPool;

    /**
     * Constructs a new ResetPasswordAttemptsEvent.
     */
    public ResetPasswordAttemptsEvent() {
        connectionPool = CDI.current().select(ConnectionPool.class).get();
    }

    /**
     * Executes the resetting of password attempts.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public void run() {
        try {
            LOGGER.info("Resetting password attempts");
            resetPasswordAttempts();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error resetting password attempts", e);
        }
    }

    /**
     * Resets password attempts.
     */
    private void resetPasswordAttempts() {
        Connection connection = connectionPool.getConnection();
        try {
            UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
            userRepository.resetPasswordAttempts();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}

