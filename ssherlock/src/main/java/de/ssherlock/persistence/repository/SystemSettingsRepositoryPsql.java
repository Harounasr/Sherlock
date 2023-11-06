package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.SystemSettings;

import java.sql.Connection;
import java.util.logging.Logger;

public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {

    private Logger logger;
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
