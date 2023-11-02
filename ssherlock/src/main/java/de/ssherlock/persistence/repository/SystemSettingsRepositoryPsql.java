package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.SystemSettings;

public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {

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
