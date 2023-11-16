package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.SystemSettingsRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.logging.Logger;
/**
 * The SystemService class provides functionality for system-related operations.
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
    private final ConnectionPoolPsql connectionPoolPsql;

    /**
     * Constructs a SystemService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to SystemService.
     * @param connectionPoolPsql The connectionPoolPsql to be used for managing database connections.
     */
    @Inject
    public SystemService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }

    /**
     * Retrieves the system settings.
     *
     * @return The system settings.
     */
    public SystemSettings getSystemSettings() {
        Connection connection = connectionPoolPsql.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        return repository.fetchSystemSettings();
    }

    /**
     * Updates the system settings.
     *
     * @param systemSettings The updated system settings.
     */
    public void updateSystemSettings(SystemSettings systemSettings) {
        Connection connection = connectionPoolPsql.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        repository.updateSystemSettings(systemSettings);
    }

    /**
     * Converts a file (Part) to a byte array.
     *
     * @param filePart The Part representing the file to be converted.
     * @return The byte array representation of the file.
     * @throws IOException If an I/O error occurs during file conversion.
     */
    private byte[] convertFileToByteArray(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream()) {
            int fileSize = (int) filePart.getSize();
            byte[] buffer = new byte[fileSize];
            int bytesRead;

            bytesRead = inputStream.read(buffer, 0, fileSize);

            if (bytesRead >= 0) {
                return buffer;
            } else {
                return null; // Handle the case where no bytes were read
            }
        }
    }
}

