package de.ssherlock.persistence.repository;

import de.ssherlock.persistence.transaction.TransactionPsql;

import java.sql.Connection;

/**
 * Base class for PostgreSQL repositories providing a database connection.
 */
public class RepositoryPsql {

    /** The database connection. */
    protected Connection connection;

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public RepositoryPsql(Connection connection) {
        this.connection = connection;
    }
}
