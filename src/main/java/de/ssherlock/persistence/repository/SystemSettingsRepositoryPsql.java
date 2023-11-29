package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Implementation of SystemSettingsRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {
    /**
     * Logger instance for logging messages related to SystemSettingsRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(SystemSettingsRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public SystemSettingsRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSystemSettings(SystemSettings systemSettings) {
        String query = "UPDATE SystemSettings SET emailRegex=?, primaryColorHex=?, " +
                       "secondaryColor=?, systemName=?, logo=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, systemSettings.getEmailRegex());
            preparedStatement.setString(2, systemSettings.getPrimaryColorHex());
            preparedStatement.setString(3, systemSettings.getSecondaryColorHex());
            preparedStatement.setString(4, systemSettings.getSystemName());
            preparedStatement.setBytes(5, systemSettings.getLogo());
            preparedStatement.setInt(6, 1);

            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "Successfully updated System Settings");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Did not update System Settings");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemSettings getSystemSettings() {
        String query = "SELECT * FROM SystemSettings ORDER BY id DESC LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                SystemSettings systemSettings = new SystemSettings();
                systemSettings.setEmailRegex(resultSet.getString("emailRegex"));
                systemSettings.setPrimaryColorHex(resultSet.getString("primaryColorHex"));
                systemSettings.setSecondaryColorHex(resultSet.getString("secondaryColor"));
                systemSettings.setSystemName(resultSet.getString("systemName"));
                systemSettings.setLogo(resultSet.getBytes("logo"));
                return systemSettings;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
