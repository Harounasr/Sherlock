package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.SystemSettings;

/**
 * Interface for interacting with a repository of SystemSettings in the database.
 *
 * @author Leon Höfling
 */
public interface SystemSettingsRepository {

  /**
   * Updates the SystemSettings entity in the database.
   *
   * @param systemSettings The SystemSettings entity to be updated.
   */
  void updateSystemSettings(SystemSettings systemSettings);

  /**
   * Fetches the SystemSettings entity from the database.
   *
   * @return The fetched SystemSettings entity, or null if not found.
   */
  SystemSettings getSystemSettings();
}
