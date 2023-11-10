package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.SystemSettings;

import java.sql.Connection;
import java.util.logging.Logger;

public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {

    private final Logger logger = LoggerCreator.get(SystemSettingsRepositoryPsql.class);
    public SystemSettingsRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void updateSystemSettings(SystemSettings systemSettings) {

    }

    @Override
    public SystemSettings fetchSystemSettings() {
        return null;
    }
}
