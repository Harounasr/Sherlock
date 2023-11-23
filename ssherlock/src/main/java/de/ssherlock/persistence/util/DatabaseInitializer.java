package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.servlet.ServletContextEvent;

/**
 * Offers database initialization functionalities.
 *
 * @author Victor Vollmann
 */
public final class DatabaseInitializer {

    /**
     * The logger of this class.
     */
    private static final SerializableLogger logger = LoggerCreator.get(DatabaseInitializer.class);

    /**
     * The path to the database initialization file.
     */
    private static final String DATABASE_INITIALIZATION_PATH = "";

    /**
     * Default private empty constructor.
     */
    private DatabaseInitializer() {

    }

    /**
     * Initializes the database schema,
     * if it has not already been initialized.
     *
     * @param sce The Servlet Context Event for initialization.
     */
    public static void initialize(ServletContextEvent sce) {

    }

}
