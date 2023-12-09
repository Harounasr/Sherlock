package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import java.util.logging.Level;

/**
 * Offers database initialization functionalities.
 *
 * @author Victor Vollmann
 */
public final class DatabaseInitializer {

    /** The Logger of this class. */
    private static final SerializableLogger LOGGER = LoggerCreator.get(DatabaseInitializer.class);

    /**
     * The path to the database initialization file.
     */
    private static final String DATABASE_INITIALIZATION_PATH =
            "WEB-INF/classes/de/ssherlock/sql/create_scheme.sql";

    /**
     * Default private empty constructor.
     */
    private DatabaseInitializer() {}

    /**
     * Initializes the database.
     *
     * @param connection A connection to the database.
     */
    public static void initializeDatabase(
            Function<String, InputStream> resourceFetcher, Connection connection) {
        String sqlScript;
        try (InputStream input = resourceFetcher.apply(DATABASE_INITIALIZATION_PATH)) {
            if (input == null) {
                throw new IOException("Could not find the file " + DATABASE_INITIALIZATION_PATH + ".");
            }
            sqlScript = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            LOGGER.fine("DB init loaded.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "DB init could not be loaded " + e.getMessage(), e);
            throw new RuntimeException();
        }
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sqlScript);
                LOGGER.info("Database initialized.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database could not be initialized " + e.getMessage(), e);
            throw new RuntimeException();
        }
    }
}
