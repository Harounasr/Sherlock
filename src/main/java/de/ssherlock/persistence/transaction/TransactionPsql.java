package de.ssherlock.persistence.transaction;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceDBAccessException;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Implementation of the Transaction interface for managing transactions with a PostgreSQL database.
 *
 * @author Leon Föckersperger
 */
public class TransactionPsql implements Transaction {

    /**
     * Database connection associated with the transaction.
     */
    private Connection connection;

    /**
     * Logger instance for logging messages related to the TransactionPsql class.
     */
    private final SerializableLogger logger;

    /**
     * Indicates whether the transaction was already terminated.
     */
    private boolean isTerminated;

    /**
     * Indicates whether the transaction was already initiated.
     */
    private boolean isInitiated;

    /**
     * Default constructor.
     *
     * @param logger The logger instance.
     */
    @Inject
    public TransactionPsql(SerializableLogger logger) {
        this.logger = logger;
        isInitiated = false;
        isTerminated = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws PersistenceDBAccessException {
        if (isInitiatedAndNotTerminated()) {
            logger.finest("Starting commit of transaction...");
            try {
                connection.commit();
                logger.fine("Transaction was committed.");
            } catch (SQLException e) {
                logger.log(Level.WARNING, "transaction could not be committed");
                throw new PersistenceDBAccessException("Error while committing transaction.", e);
            } finally {
                CDI.current().select(ConnectionPool.class).get().releaseConnection(connection);
                logger.fine("connection released to pool");
            }
            terminate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abort() {
        if (isInitiatedAndNotTerminated()) {
            logger.warning("Starting rollback of transaction.");
            try {
                connection.rollback();
                logger.fine("Rollback of transaction was successful.");
            } catch (SQLException e) {
                logger.log(Level.WARNING, "transaction could not be aborted. Error during rollback");
                // Database is probably not available since otherwise there would be no SqlException.
            } finally {
                CDI.current().select(ConnectionPool.class).get().releaseConnection(connection);
                logger.fine("connection released to pool");
            }
            terminate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        abort();
    }

    /**
     * Retrieves the database connection associated with the transaction.
     *
     * @return The database connection.
     * @throws PersistenceDBAccessException if the connection could not be retrieved
     */
    public Connection getConnection() throws PersistenceDBAccessException {
        if (connection == null) {
            connection = CDI.current().select(ConnectionPool.class).get().getConnection();
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new PersistenceDBAccessException("Could not set transaction auto commit.");
            }
            isInitiated = true;
        }
        return connection;
    }

    /**
     * checks if the transaction was already terminated and logs if it was already terminated before the call.
     *
     * @return {@code true} if the connection was aborted or committed.
     */
    private boolean isInitiatedAndNotTerminated() {
        if (!isInitiated) {
            logger.fine("Connection was not fetched from pool.");
            return false;
        }
        if (isTerminated) {
            logger.fine("Transaction was already committed or aborted.");
            return false;
        }
        return true;
    }

    /**
     * Marks the transaction as terminated and logs it.
     */
    private void terminate() {
        isTerminated = true;
        logger.finest("End of transaction.");
    }
}
