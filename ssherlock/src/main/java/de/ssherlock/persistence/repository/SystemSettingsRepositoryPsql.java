package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.SystemSettings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of SystemSettingsRepository for PostgreSQL database.
 */
public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {
    /**
     * Logger instance for logging messages related to SystemSettingsRepositoryPsql.
     */
    private final Logger logger = LoggerCreator.get(SystemSettingsRepositoryPsql.class);

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
            preparedStatement.setString(1, systemSettings.emailRegex());
            preparedStatement.setString(2, systemSettings.primaryColorHex());
            preparedStatement.setString(3, systemSettings.secondaryColorHex());
            preparedStatement.setString(4, systemSettings.systemName());
            preparedStatement.setBytes(5, systemSettings.logo());
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
    public SystemSettings fetchSystemSettings() {
        String query = "SELECT * FROM SystemSettings ORDER BY id DESC LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                SystemSettings systemSettings = new SystemSettings(
                        resultSet.getString("emailRegex"),
                        resultSet.getString("primaryColorHex"),
                        resultSet.getString("secondaryColor"),
                        resultSet.getString("systemName"),
                        resultSet.getBytes("logo"),
                        null
                );
                return systemSettings;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static Image createDummyImage(int width, int height, Color color) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();

        // Fill the image with the specified color
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);

        return bufferedImage;
    }

}
