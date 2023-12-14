package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.config.Configuration;
import de.ssherlock.persistence.exception.DBUnavailableException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

/**
 * Application-scoped connection pool class for managing database connections. This class provides
 * methods to create, borrow, and release database connections.
 *
 * @author Victor Vollmann
 */
@Named
@ApplicationScoped
@SuppressFBWarnings(
        value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
        justification = "Suppress warnings about transient fields not being restored")
public class ConnectionPool implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Connection validation timeout.
     */
    private static final int VALIDATION_TIMEOUT = 2;

    /**
     * Configuration instance for obtaining database connection settings.
     */
    private final Configuration configuration;

    /**
     * Logger instance for logging messages related to the ConnectionPoolPsql class.
     */
    private final SerializableLogger logger;

    /**
     * Queue of available database connections.
     */
    private final transient Queue<Connection> connections = new ConcurrentLinkedQueue<>();

    /**
     * List of borrowed database connections.
     */
    private final transient Set<Connection> borrowedConnections = new HashSet<>();

    /**
     * Default constructor for creating a ConnectionPoolPsql instance.
     *
     * @param configuration The configuration of the database.
     * @param logger        The logger of this class.
     */
    @Inject
    public ConnectionPool(Configuration configuration, SerializableLogger logger) {
        this.configuration = configuration;
        this.logger = logger;
    }

    /**
     * Initializes the connection pool after creation. Loads the database driver and creates the
     * initial pool of database connections.
     */
    public synchronized void init() {
        logger.info("New ConnectionPool created.");
        loadDriver();
        for (int i = 0; i < configuration.getDbNumConnections(); i++) {
            connections.offer(createConnection());
        }
        logger.log(
                Level.INFO,
                "Created " + connections.size() + " connections to database: " + configuration.getDbName());
    }

    /**
     * Destroys the connection pool. Closes all available and borrowed connections and clears the
     * connection queues.
     */
    public synchronized void destroy() {
        connections.removeIf(Objects::isNull);
        borrowedConnections.removeIf(Objects::isNull);
        for (Connection connection : connections) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    logger.finest("Successfully closed connection.");
                }
            } catch (SQLException e) {
                logger.warning("Failed to close connection.");
            }
            logger.finest("Successfully closed connection.");
        }
        for (Connection connection : borrowedConnections) {
            try {
                if (connection != null && !connection.isClosed()) {
                    if (!connection.getAutoCommit()) {
                        connection.rollback();
                        logger.finest("Successfully rolled back connection.");
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                logger.warning("Borrowed connection could not be rolled back or closed.");
            }
        }
        connections.clear();
        borrowedConnections.clear();
        logger.info("Successfully destroyed connection pool.");
    }

    /**
     * Retrieves a database connection from the connection pool. If the pool is empty, the method
     * waits until a connection becomes available.
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
                if (connection != null && !connection.isClosed()) {
                    if (!connection.getAutoCommit()) {
                        connection.rollback();
                        logger.finest("Connection successfully rolled back.");
                    }
                    connections.offer(connection);
                    logger.info("Connection was released successfully");
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error during connection release: ", e);
            }
        } else {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    logger.warning("Connection was not borrowed, so it was closed.");
                }
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
            conn =
                    DriverManager.getConnection(
                            String.format(
                                    "%s://%s/%s",
                                    configuration.getDbConnectionPrefix(),
                                    configuration.getDbHost(),
                                    configuration.getDbName()),
                            configuration.getConnectionProperties());
            logger.finer("Created a new connection to the database.");
        } catch (SQLException e) {
            throw new DBUnavailableException("Connection could not be created.", e);
        }
        return conn;
    }

    /**
     * Loads the database driver. Throws an error if the driver is not found.
     */
    private synchronized void loadDriver() {
        try {
            Class.forName(configuration.getDbDriver());
            logger.info("Database Driver loaded.");
        } catch (ClassNotFoundException e) {
            throw new DBUnavailableException("DB Driver could not be found.", e);
        }
    }
}
