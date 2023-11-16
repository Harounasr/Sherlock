package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
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
    private final Logger logger = LoggerCreator.get(SubmissionRepositoryPsql.class);
    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public SubmissionRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertSubmission(Submission submission) {

    }

    @Override
    public void updateSubmission(Submission submission) {

    }

    @Override
    public void deleteSubmission(long id) {

    }

    @Override
    public Submission fetchSubmission(long id) {
        return null;
    }

    @Override
    public List<Submission> fetchSubmissions(Predicate<Submission> predicate) {
        return null;
    }

    @Override
    public long getMaxId() {
        return 0;
    }
}
