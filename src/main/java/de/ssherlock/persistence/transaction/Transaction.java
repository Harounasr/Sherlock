package de.ssherlock.persistence.transaction;

import de.ssherlock.persistence.exception.PersistenceDBAccessException;
import java.sql.Connection;


/**
 * Interface for managing transactions, providing methods for committing and aborting transactions.
 *
 * @author Leon Föckersperger
 */
public interface Transaction extends AutoCloseable {

    /**
     * Commits the transaction.
     *
     * @throws PersistenceDBAccessException if the transaction could not be committed.
     */
    void commit() throws PersistenceDBAccessException;

    /**
     * Aborts (rolls back) the transaction.
     */
    void abort();

    /**
     * Closes this resource.
     */
    void close();

    /**
     * Gets the connection.
     *
     * @return The connection
     */
    Connection getConnection();
}
