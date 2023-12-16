package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  public void insertSubmission(Submission submission) {
      String sqlQuery =
              """
                    INSERT INTO submission (timestamp_submission, student_username, exercise_id)
                    VALUES (?, ?, ?) RETURNING id
      
                    """;
      try (PreparedStatement submissionStatement = getConnection().prepareStatement(sqlQuery)) {
          submissionStatement.setTimestamp(1, submission.getTimestamp());
          submissionStatement.setString(2, submission.getUser());
          submissionStatement.setLong(3, submission.getExerciseId());

          try (ResultSet resultSet = submissionStatement.executeQuery()) {
              if (resultSet.next()) {
                  long submissionId = resultSet.getLong("id");
                  submission.setId(submissionId);
                  insertSubmissionFiles(getConnection(), submission);
                  insertCheckerResults(getConnection(), submission);
              }
          } catch (SQLException e) {

          }

      } catch (SQLException e) {

      }
  }

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

    private void insertSubmissionFiles(Connection connection, Submission submission)
            throws SQLException {
        String insertSubmissionFileQuery =
                "INSERT INTO submission_file (submission_id, file_name, file) VALUES (?, ?, ?)";

        try (PreparedStatement submissionFileStatement =
                     connection.prepareStatement(insertSubmissionFileQuery)) {
            for (SubmissionFile submissionFile : submission.getSubmissionFiles()) {
                submissionFileStatement.setLong(1, submission.getId());
                submissionFileStatement.setString(2, submissionFile.getName());
                submissionFileStatement.setBytes(3, submissionFile.getBytes());

                submissionFileStatement.addBatch();
            }

            submissionFileStatement.executeBatch();
        }
    }

    private void insertCheckerResults(Connection connection, Submission submission)
            throws SQLException {

        if (submission.getCheckerResults() != null) {
            String insertCheckerResultQuery =
                    "INSERT INTO checker_result (exercise_id, checker_id, submission_id, has_passed, result_description) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement checkerResultStatement =
                         connection.prepareStatement(insertCheckerResultQuery)) {
                for (CheckerResult checkerResult : submission.getCheckerResults()) {
                    Checker checker = checkerResult.getChecker();

                    checkerResultStatement.setLong(1, checker.getExerciseId());
                    checkerResultStatement.setLong(2, checker.getId());
                    checkerResultStatement.setLong(3, submission.getId());
                    checkerResultStatement.setBoolean(4, checkerResult.isPassed());
                    checkerResultStatement.setString(5, checkerResult.getStackTrace());

                    checkerResultStatement.addBatch();
                }

                // Execute batch insert for checker results
                checkerResultStatement.executeBatch();
            }
        }
    }
}


