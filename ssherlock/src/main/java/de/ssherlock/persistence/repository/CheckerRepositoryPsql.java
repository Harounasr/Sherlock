package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Checker;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Implementation of CheckerRepository for PostgreSQL database.
 */
public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {
    /**
     * Logger instance for logging messages related to CheckerRepositoryPsql.
     */
    private final Logger logger = LoggerCreator.get(CheckerRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public CheckerRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertChecker(Checker checker) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChecker(Checker checker) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteChecker(long id) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Checker fetchChecker(long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Checker> fetchCheckers(Predicate<Checker> predicate) {
        return null;
    }
}
