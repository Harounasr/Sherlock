package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Testate;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
/**
 * Implementation of TestateRepository for PostgreSQL database.
 */
public class TestateRepositoryPsql extends RepositoryPsql implements TestateRepository {
    /**
     * Logger instance for logging messages related to TestateRepositoryPsql.
     */
    private final Logger logger = LoggerCreator.get(TestateRepositoryPsql.class);
    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public TestateRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertEvaluation(Testate testate) {

    }

    @Override
    public void updateEvaluation(Testate testate) {

    }

    @Override
    public void deleteEvaluation(long id) {

    }

    @Override
    public Testate fetchEvaluation(long id) {
        return null;
    }

    @Override
    public List<Testate> fetchEvaluations(Predicate<Testate> predicate) {
        return null;
    }
}
