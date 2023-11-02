package de.ssherlock.persistence.transaction;

import java.sql.Connection;

public class TransactionPsql implements Transaction {

    Connection connection;

    public TransactionPsql() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void abort() {

    }

    public Connection getConnection() {
        return connection;
    }
}
