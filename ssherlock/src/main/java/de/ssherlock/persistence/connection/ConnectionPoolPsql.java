package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPoolPsql {

    private static ConnectionPoolPsql INSTANCE;
    private DatabaseConfiguration configuration;
    private final Logger logger = LoggerCreator.get(ConnectionPoolPsql.class);
    private final Queue<Connection> connections = new LinkedList<>();
    private final List<Connection> borrowedConnections = new LinkedList<>();

    private ConnectionPoolPsql() {
        logger.log(Level.INFO, "New ConnectionPool created");
    }

    public static synchronized ConnectionPoolPsql getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPoolPsql();
        }
        return INSTANCE;
    }

    public synchronized void init() {
        configuration = DatabaseConfiguration.getInstance();
        loadDriver();
        for (int i = 0; i < configuration.getMaxConnections(); i++) {
            logger.log(Level.INFO, "creating connection");
            connections.offer(createConnection());
        }
        logger.log(Level.INFO, String.valueOf(configuration.getMaxConnections()));
        logger.log(Level.INFO, String.valueOf(connections.size()));
    }

    public synchronized void destroy() {
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

    public void releaseConnection(Connection connection) {
        borrowedConnections.remove(connection);
        connections.offer(connection);
    }

    private synchronized Connection createConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + configuration.getHost() + "/vollmanv", configuration.getConnectionProperties());
            logger.log(Level.INFO, "created connection");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not create a new connection.", e);
            throw new RuntimeException(e);
        }
        return conn;
    }

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
