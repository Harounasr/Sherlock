package de.ssherlock.persistence.connection;

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
    private Logger logger;
    private final Queue<Connection> connections = new LinkedList<>();
    private final List<Connection> borrowedConnections = new LinkedList<>();

    private ConnectionPoolPsql() {

    }

    public static ConnectionPoolPsql getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPoolPsql();
        }
        return INSTANCE;
    }

    public void init() {
        configuration = DatabaseConfiguration.getInstance();
        loadDriver();
        for (int i = 0; i < 10; i++) {
            connections.offer(createConnection());
        }
    }

    public void destroy() {

    }

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

    public void releaseConnection(Connection connection) {
        borrowedConnections.remove(connection);
        connections.offer(connection);
    }

    private Connection createConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + configuration.getHost() + "/" + configuration.getName(), configuration.getConnectionProperties());
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create a new connection.", e);
            throw new RuntimeException(e);
        }
        return conn;
    }

    private void loadDriver() {
        try {
            Class.forName(configuration.getDriver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "DB Driver not found.");
            throw new Error("DB Driver not found.", e);
        }
    }
}
