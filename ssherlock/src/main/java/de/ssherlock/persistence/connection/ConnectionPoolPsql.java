package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.config.Configuration;
import jakarta.annotation.PostConstruct;
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
import java.util.logging.Logger;

/**
 * Application-scoped connection pool class for managing PostgreSQL database connections.
 * This class provides methods to create, borrow, and release database connections.
 *
 * @author Victor Vollmann
 */
@Named
@ApplicationScoped
public class ConnectionPoolPsql implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Configuration instance for obtaining database connection settings.
     */
    @Inject
    private Configuration configuration;

    /**
     * Logger instance for logging messages related to the ConnectionPoolPsql class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * Queue of available database connections.
     */
    private final Queue<Connection> connections = new LinkedList<>();

    /**
     * List of borrowed database connections.
     */
    private final List<Connection> borrowedConnections = new LinkedList<>();

    /**
     * Default constructor for creating a ConnectionPoolPsql instance.
     */
    public ConnectionPoolPsql() {

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
            logger.log(Level.FINEST, "Try to close connection.");
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close Connection.");
                }
                logger.log(Level.FINEST, "Closed connection successfully.");
            }
        }
        for (Connection conn : borrowedConnections) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Borrowed Connection from Connection Pool could not be rollback successfully.");
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Borrowed Connection from Connection Pool could not be closed.");
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
        while (connections.isEmpty()) {
            try {
                wait(System.currentTimeMillis() + 2000);
            } catch (InterruptedException e) {

            }
        }
        Connection conn = connections.poll();
        borrowedConnections.add(conn);
        return conn;
    }

    /**
     * Releases a borrowed database connection back to the connection pool.
     *
     * @param connection The database connection to be released.
     */
    public synchronized void releaseConnection(Connection connection) {
        borrowedConnections.remove(connection);
        connections.offer(connection);
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
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * Loads the PostgreSQL database driver.
     * Throws an error if the driver is not found.
     */
    private synchronized void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "Database Driver loaded.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, "DB Driver not found.");
            throw new Error("DB Driver not found.", e);
        }
    }
}
