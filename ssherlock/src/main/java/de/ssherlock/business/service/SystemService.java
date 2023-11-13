package de.ssherlock.business.service;

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
import java.sql.Connection;
import java.util.logging.Logger;

@Dependent
public class SystemService {
    @Inject
    private Logger logger;
    @Inject
    private ConnectionPoolPsql connectionPoolPsql;
    public SystemService() {

    }
    public SystemSettings getSystemSettings() {
        Connection connection = connectionPoolPsql.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        return repository.fetchSystemSettings();
    }
    public void updateSystemSettings(SystemSettings systemSettings) {
        Connection connection = connectionPoolPsql.getConnection();
        SystemSettingsRepository repository = RepositoryFactory.getSystemSettingsRepository(RepositoryType.POSTGRESQL, connection);
        repository.updateSystemSettings(systemSettings);
    }

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
