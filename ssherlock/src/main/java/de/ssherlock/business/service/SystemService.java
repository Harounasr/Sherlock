package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.SystemSettingsRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;

/**
 * The SystemService class provides functionality for system-related operations.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class SystemService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to SystemService.
     */
    private final SerializableLogger logger;

    /**
     * Connection pool for managing database connections.
     */
    private final ConnectionPool connectionPool;

    /**
     * Constructs a SystemService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to SystemService.
     * @param connectionPool The connectionPoolPsql to be used for managing database connections.
     */
    @Inject
    public SystemService(SerializableLogger logger, ConnectionPool connectionPool) {
        this.logger = logger;
        this.connectionPool = connectionPool;
    }

    /**
     * Retrieves the system settings.
     *
     * @return The system settings.
     */
    public SystemSettings getSystemSettings() {
        Connection connection = connectionPool.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        return repository.getSystemSettings();
    }

    /**
     * Updates the system settings.
     *
     * @param systemSettings The updated system settings.
     */
    public void updateSystemSettings(SystemSettings systemSettings) {
        Connection connection = connectionPool.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        repository.updateSystemSettings(systemSettings);
    }

}

