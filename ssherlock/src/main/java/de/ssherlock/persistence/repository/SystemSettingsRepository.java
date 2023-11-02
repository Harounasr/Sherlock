package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.SystemSettings;

public interface SystemSettingsRepository {
    void updateSystemSettings(SystemSettings systemSettings);
    SystemSettings fetchSystemSettings();
}
