package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.servlet.ServletContextEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Offers database initialization functionalities.
 *
 * @author Victor Vollmann
 */
public final class DatabaseInitializer {

    /**
     * The logger of this class.
     */
    private static final SerializableLogger LOGGER = LoggerCreator.get(DatabaseInitializer.class);

    /**
     * The path to the database initialization file.
     */
    private static final String DATABASE_INITIALIZATION_PATH = "";

    /**
     * Default private empty constructor.
     */
    private DatabaseInitializer() {}

    /**
     * Initializes the database schema, if it has not already been initialized.
     *
     * @param sce The Servlet Context Event for initialization.
     * @param connection A connection to the database.
     */
    public static void initialize(ServletContextEvent sce, Connection connection) {
        String sql;
        try (InputStream input = sce.getServletContext().getResourceAsStream(DATABASE_INITIALIZATION_PATH)) {
            if (input == null) {
                throw new FileNotFoundException("database initialization file could not be found at location: " + DATABASE_INITIALIZATION_PATH + ".");
            }
            sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            LOGGER.fine("database initialization file loaded.");
        } catch (IOException e) {
            throw new RuntimeException("The database initialization file could not be read", e);
        }
        try {
            if (!alreadyInitialized(connection)) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                    LOGGER.info("The database was successfully initialized.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("The database could not be initialized", e);
        }
    }

    /**
     * Checks whether the database exists.
     *
     * @param connection The connection to the database.
     * @return Whether the database exists.
     *
     * @throws SQLException when the statement can not be executed.
     */
    private static boolean alreadyInitialized(Connection connection) throws SQLException {
        boolean result = false;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet set = statement.executeQuery("""
                SELECT EXISTS (SELECT 1 FROM information_schemata.schemata
                WHERE schema_name = 'ssherlock');
                """)) {
                if (set.next()) {
                    result = set.getBoolean(1);
                }
            }
        }
        return result;
    }
}
