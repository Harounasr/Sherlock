package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of SubmissionRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
 */
public class SubmissionRepositoryPsql extends RepositoryPsql implements SubmissionRepository {
  /** Logger instance for logging messages related to SubmissionRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(SubmissionRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public SubmissionRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertSubmission(Submission submission) {}

  /** {@inheritDoc} */
  @Override
  public Submission getSubmission(Submission submission) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public List<Submission> getSubmissions(Exercise exercise) {
    return null;
  }
}
