package de.ssherlock.business.maintenance;

import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import jakarta.inject.Inject;

import java.sql.Connection;

/**
 * Resets the Password Attempts for every user every hour.
 * @author Lennart Hohls
 */
public class ResetPasswordAttemptsEvent {
    /**
     * The Connection pool for this class.
     */
    @Inject
    private ConnectionPool connectionPool;

    /**
     * Constructs a new ResetPasswordAttemptsEvent.
     */
    public ResetPasswordAttemptsEvent(){}

    public void resetPasswordAttempts(){
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL,connection);
        userRepository.resetPasswordAttempts();
        connectionPool.releaseConnection(connection);
    }

    /**
     * Checks if UnverifiedUsersCleanEvent is currently running.
     *
     * @return true/false according to the state of UnverifiedUsersCleanEvent.
     */
    public boolean isRunning() {
        return false;
    }

    /** Shuts down the SendEmailNotificationEvent. */
    public void shutdown() {}
}

