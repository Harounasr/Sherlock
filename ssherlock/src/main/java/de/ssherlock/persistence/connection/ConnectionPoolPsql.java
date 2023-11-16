package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.Configuration;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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
 */
@Named
@ApplicationScoped
public class ConnectionPoolPsql {
    /**
     * Configuration instance for obtaining database connection settings.
     */
    @Inject
    private Configuration configuration;
    /**
     * Logger instance for logging messages related to the ConnectionPoolPsql class.
     */
    private final Logger logger = LoggerCreator.get(ConnectionPoolPsql.class);
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
    @PostConstruct
    public void afterCreate() {
        logger.log(Level.INFO, "New ConnectionPool created");
        loadDriver();
        for (int i = 0; i < configuration.getDbNumConnections(); i++) {
            logger.log(Level.INFO, "creating connection");
            connections.offer(createConnection());
        }
        logger.log(Level.INFO, String.valueOf(configuration.getDbNumConnections()));
        logger.log(Level.INFO, String.valueOf(connections.size()));
    }
    /**
     * Destroys the connection pool.
     * Closes all available and borrowed connections and clears the connection queues.
     */
    public void destroy() {
        for (Connection conn : connections) {
            logger.log(Level.FINEST, "Try to close connection.");
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close Connection.", e);
                }
                logger.log(Level.FINEST, "Closed connection successfully.");
            }
        }
        for (Connection conn : borrowedConnections) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Borrowed Connection from Connection Pool could not be rollback successfully.", e);
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Borrowed Connection from Connection Pool could not be closed.", e);
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
    public Connection getConnection() {
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
    public void releaseConnection(Connection connection) {
        borrowedConnections.remove(connection);
        connections.offer(connection);
    }
    /**
     * Creates a new database connection.
     *
     * @return A newly created database connection.
     */
    private Connection createConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + configuration.getDbHost() + "/"+ configuration.getDbName(), configuration.getConnectionProperties());
            logger.log(Level.INFO, "created connection");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    /**
     * Loads the PostgreSQL database driver.
     * Throws an error if the driver is not found.
     */
    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "driver loaded");
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, "DB Driver not found.");
            throw new Error("DB Driver not found.", e);
        }
    }
}
