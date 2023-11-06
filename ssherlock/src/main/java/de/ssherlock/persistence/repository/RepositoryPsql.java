package de.ssherlock.persistence.repository;

import de.ssherlock.persistence.transaction.TransactionPsql;

import java.sql.Connection;

public class RepositoryPsql {
    
    Connection connection;

    public RepositoryPsql(Connection connection) {
        this.connection = connection;
    }

}
