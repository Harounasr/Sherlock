package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Submission;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Implementation of SubmissionRepository for PostgreSQL database.
 */
public class SubmissionRepositoryPsql extends RepositoryPsql implements SubmissionRepository {
    /**
     * Logger instance for logging messages related to SubmissionRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(SubmissionRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public SubmissionRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertSubmission(Submission submission) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submission getSubmission(long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Submission> getSubmissions(long exerciseId) {
        return null;
    }

}
