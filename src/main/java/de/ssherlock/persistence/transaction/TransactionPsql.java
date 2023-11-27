package de.ssherlock.persistence.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the Transaction interface for managing transactions with a PostgreSQL database.
 *
 * @author Victor Vollmann
 */
public class TransactionPsql implements Transaction {

    /**
     * Database connection associated with the transaction.
     */
    private final Connection connection;

    /**
     * Constructs a TransactionPsql instance with the specified database connection.
     *
     * @param connection The database connection associated with the transaction.
     */
    public TransactionPsql(Connection connection) {
        this.connection = connection;
    }

    /**
     * Commits the transaction if the connection is not null and auto-commit is disabled.
     *
     * @throws SQLException If an SQL exception occurs during the commit operation.
     */
    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    /**
     * Aborts (rolls back) the transaction if the connection is not null and auto-commit is disabled.
     *
     * @throws SQLException If an SQL exception occurs during the abort operation.
     */
    @Override
    public void abort() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    /**
     * Closes the database connection associated with the transaction if it is not null and not closed.
     *
     * @throws SQLException If an SQL exception occurs during the connection closure.
     */
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Retrieves the database connection associated with the transaction.
     *
     * @return The database connection.
     */
    public Connection getConnection() {
        return connection;
    }
}
