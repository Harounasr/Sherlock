package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.config.Configuration;
import de.ssherlock.persistence.exception.DBUnavailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

/**
 * Application-scoped connection pool class for managing PostgreSQL database connections.
 * This class provides methods to create, borrow, and release database connections.
 *
 * @author Victor Vollmann
 */
@Named
@ApplicationScoped
@SuppressWarnings("spotbugs:SE_TRANSIENT_FIELD_NOT_RESTORED")
public class ConnectionPool implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Connection validation timeout
     */
    private static final int VALIDATION_TIMEOUT = 2;

    /**
     * Configuration instance for obtaining database connection settings.
     */
    @Inject private Configuration configuration;

    /**
     * Logger instance for logging messages related to the ConnectionPoolPsql class.
     */
    @Inject private SerializableLogger logger;

    /**
     * Queue of available database connections.
     */
    private final transient Queue<Connection> connections = new LinkedList<>();

    /**
     * List of borrowed database connections.
     */
    private final transient List<Connection> borrowedConnections = new LinkedList<>();

    /**
     * Default constructor for creating a ConnectionPoolPsql instance.
     */
    public ConnectionPool() {

    }

    /**
     * Initializes the connection pool after creation.
     * Loads the database driver and creates the initial pool of database connections.
     */
    public synchronized void init() {
        logger.log(Level.INFO, "New ConnectionPool created.");
        loadDriver();
        for (int i = 0; i < configuration.getDbNumConnections(); i++) {
            connections.offer(createConnection());
        }
        logger.log(Level.INFO, "Created " + connections.size() + " connection to database: " + configuration.getDbName());
    }

    /**
     * Destroys the connection pool.
     * Closes all available and borrowed connections and clears the connection queues.
     */
    public synchronized void destroy() {
        for (Connection conn : connections) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.warning("Failed to close Connection.");
                }
                logger.finest("Successfully closed connection.");
            }
        }
        for (Connection conn : borrowedConnections) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    logger.warning("Borrowed Connection could not be rolled back.");
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.warning("Borrowed Connection could not be closed.");
                }
            }
        }
        connections.clear();
        borrowedConnections.clear();
    }

    /**
     * Retrieves a database connection from the connection pool.
     * If the pool is empty, the method waits until a connection becomes available.
     *
     * @return A database connection.
     */
    public synchronized Connection getConnection() {
        long timeout = System.currentTimeMillis() + configuration.getDbTimeoutMillis();
        while (connections.isEmpty() && System.currentTimeMillis() < timeout) {
            try {
                wait(timeout - System.currentTimeMillis());
            } catch (InterruptedException e) {
                logger.severe("The thread got interrupted while waiting for an available connection.");
            }
        }
        if (connections.isEmpty()) {
            logger.severe("No database connections currently available in the pool.");
            throw new DBUnavailableException("No database connections currently available in the pool.");
        } else {
            Connection conn = connections.poll();
            borrowedConnections.add(conn);
            return conn;
        }
    }

    /**
     * Releases a borrowed database connection back to the connection pool.
     *
     * @param connection The database connection to be released.
     */
    public synchronized void releaseConnection(Connection connection) {
        if (borrowedConnections.remove(connection)) {
            try {
                if (isValidConnection(connection)) {
                    connection.rollback();
                    logger.finest("Connection successfully rolled back.");
                } else {
                    connection = createConnection();
                    logger.finest("Connection successfully reset.");
                }
            } catch (SQLException e) {
                connection = createConnection();
            } finally {
                logger.info("Connection was released successfully");
                connections.offer(connection);
            }
        } else {
            try {
                connection.close();
                logger.warning("Connection was not borrowed, so it was closed.");
            } catch (SQLException e) {
                logger.warning("Connection was not borrowed and could not be closed.");
            }
        }
    }

    /**
     * Creates a new database connection.
     *
     * @return A newly created database connection.
     */
    private synchronized Connection createConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + configuration.getDbHost() + "/"+ configuration.getDbName(), configuration.getConnectionProperties());
        } catch (SQLException e) {
            throw new DBUnavailableException("Connection could not be created.", e);
        }
        return conn;
    }

    /**
     * Loads the PostgreSQL database driver.
     * Throws an error if the driver is not found.
     */
    private synchronized void loadDriver() {
        try {
            Class.forName(configuration.getDbDriver());
            logger.info("Database Driver loaded.");
        } catch (ClassNotFoundException e) {
            throw new DBUnavailableException("DB Driver could not be found.", e);
        }
    }

    /**
     * Validates a given connection.
     *
     * @param connection The connection to validate.
     * @return Whether the connection is valid.
     */
    private boolean isValidConnection(Connection connection) {
        try {
            return connection.isValid(VALIDATION_TIMEOUT);
        } catch (SQLException e) {
            logger.warning("Connection could not be validated.");
            return false;
        }
    }
}
