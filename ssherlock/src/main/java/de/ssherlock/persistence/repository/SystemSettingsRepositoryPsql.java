package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.SystemSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemSettingsRepositoryPsql extends RepositoryPsql implements SystemSettingsRepository {

    private final Logger logger = LoggerCreator.get(SystemSettingsRepositoryPsql.class);
    public SystemSettingsRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void updateSystemSettings(SystemSettings systemSettings) {
        String query = "UPDATE SystemSettings SET emailRegex=?, primaryColorHex=?, " +
                "secondaryColor=?, systemName=?, logo=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            // TODO Figure out how to store different formats
            ImageIO.write((BufferedImage) systemSettings.logo(), "png", os);
            preparedStatement.setString(1, systemSettings.emailRegex());
            preparedStatement.setString(2, systemSettings.primaryColorHex());
            preparedStatement.setString(3, systemSettings.secondaryColorHex());
            preparedStatement.setString(4, systemSettings.systemName());
            preparedStatement.setBytes(5, os.toByteArray());
            preparedStatement.setInt(6, 1);

            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "Successfully updated System Settings");
        } catch (SQLException | IOException e) {
            logger.log(Level.INFO, "Did not update System Settings");
        }
    }

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
                        ImageIO.read(new ByteArrayInputStream(resultSet.getBytes("logo"))),
                        null
                );
                return systemSettings;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
