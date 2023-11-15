package de.ssherlock.persistence.transaction;

import de.ssherlock.persistence.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionPsql implements Transaction {

    private final Connection connection;

    public TransactionPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @Override
    public void abort() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
