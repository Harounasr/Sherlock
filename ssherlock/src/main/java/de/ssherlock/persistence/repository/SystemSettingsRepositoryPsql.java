package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.SystemSettings;

import java.util.logging.Logger;

public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {

    private Logger logger;
    public SystemSettingsRepositoryPsql() {
        super();
    }

    @Override
    public void updateSystemSettings(SystemSettings systemSettings) {

    }

    @Override
    public SystemSettings fetchSystemSettings() {
        return null;
    }
}
