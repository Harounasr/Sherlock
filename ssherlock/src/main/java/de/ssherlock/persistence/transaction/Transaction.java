package de.ssherlock.persistence.transaction;

import java.sql.SQLException;

/**
 * Interface for managing transactions, providing methods for committing and aborting transactions.
 */
public interface Transaction extends AutoCloseable {

    /**
     * Commits the transaction.
     *
     * @throws SQLException If an SQL exception occurs during the commit operation.
     */
    void commit() throws SQLException;

    /**
     * Aborts (rolls back) the transaction.
     *
     * @throws SQLException If an SQL exception occurs during the abort operation.
     */
    void abort() throws SQLException;
}
