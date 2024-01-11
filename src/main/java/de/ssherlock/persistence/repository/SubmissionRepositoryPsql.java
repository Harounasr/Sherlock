package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementation of SubmissionRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
 */
@SuppressFBWarnings("DM_DEFAULT_ENCODING")
@SuppressWarnings("checkstyle:MagicNumber")
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
        String sqlQuery = """
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Submission getSubmission(Submission submission) {
        List<SubmissionFile> submissionFiles = new ArrayList<>();
        String submissionFilesQuery = """
                                      SELECT * FROM submission_file
                                      WHERE submission_id = ?;
                                      """;
        try (PreparedStatement statement = getConnection().prepareStatement(submissionFilesQuery)) {
            statement.setLong(1, submission.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setName(resultSet.getString("file_name"));
                    submissionFile.setBytes(resultSet.getBytes("file"));
                    submissionFiles.add(submissionFile);
                } while (resultSet.next());
            } else {
                // Handle the case where no submission files were found
                logger.log(Level.INFO, "No submission files found for submission ID: " + submission.getId());
                return new Submission();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "Submission files could not be loaded." + e);
            return new Submission();
        }

        String sqlQuery = """
                          SELECT * FROM submission
                          WHERE id = ?;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, submission.getId());
            ResultSet resultSet = statement.executeQuery();
            Submission resultSubmission = new Submission();
            if (resultSet.next()) {
                resultSubmission.setSubmissionFiles(submissionFiles);
                resultSubmission.setUser(resultSet.getString("student_username"));
                resultSubmission.setTutor(resultSet.getString("tutor_username"));
                resultSubmission.setId(resultSet.getLong("id"));
                resultSubmission.setExerciseId(resultSet.getLong("exercise_id"));
            }
            return resultSubmission;
        } catch (SQLException e) {
            logger.log(Level.INFO, "Submission could not be loaded." + e);
            return new Submission();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Submission> getSubmissions(Exercise exercise, List<User> students) {
        List<Submission> submissions = new ArrayList<>();
        students.forEach(student -> submissions.addAll(getSubmissionsForStudent(exercise, student)));
        return submissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Submission> getSubmissionsForStudent(Exercise exercise, User user) {
        String sqlQuery = """
                          SELECT
                              s.id,
                              s.timestamp_submission,
                              s.tutor_username,
                              CASE\s
                                  WHEN COUNT(*) = COUNT(CASE WHEN cr.has_passed THEN 1 END) THEN true
                                  ELSE false
                              END AS checkers_passed
                          FROM
                              submission s
                          LEFT JOIN checker_result cr ON s.id = cr.submission_id
                          LEFT JOIN checker c ON cr.checker_id = c.id AND c.is_required = TRUE
                          WHERE
                              s.student_username = ? AND s.exercise_id = ?
                          GROUP BY s.id;
                          """;
        List<Submission> submissions = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, user.getUsername());
            statement.setLong(2, exercise.getId());
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Submission submission = new Submission();
                    submission.setUser(user.getUsername());
                    submission.setExerciseId(exercise.getId());
                    submission.setId(result.getLong("id"));
                    submission.setTutor(result.getString("tutor_username"));
                    submission.setTimestamp(result.getTimestamp("timestamp_submission"));
                    submission.setPassedCheckers(result.getBoolean("checkers_passed"));
                    submissions.add(submission);
                }
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
        return submissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Submission> getSubmissionsForTutor(Exercise exercise, User user) {
        String sqlQuery = """
                          SELECT
                              s.id,
                              s.timestamp_submission,
                              s.student_username,
                              EXISTS (
                                  SELECT 1
                                  FROM testate t
                                  WHERE t.submission_id = s.id
                              ) AS testate_created
                          FROM
                              submission s
                          WHERE
                              s.tutor_username = ? AND s.exercise_id = ? AND
                              s.timestamp_submission = (
                                  SELECT MAX(timestamp_submission)
                                  FROM submission sub
                                  WHERE sub.student_username = s.student_username AND sub.exercise_id = s.exercise_id
                              )
                          GROUP BY s.id;
                          """;
        List<Submission> submissions = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, user.getUsername());
            statement.setLong(2, exercise.getId());
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Submission submission = new Submission();
                    submission.setTutor(user.getUsername());
                    submission.setExerciseId(exercise.getId());
                    submission.setId(result.getLong("id"));
                    submission.setTutor(result.getString("tutor_username"));
                    submission.setTimestamp(result.getTimestamp("timestamp_submission"));
                    submission.setTestateCreated(result.getBoolean("testate_created"));
                    submissions.add(submission);
                }
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
        return submissions;
    }

    private void insertSubmissionFiles(Connection connection, Submission submission) throws SQLException {
        String insertSubmissionFileQuery = "INSERT INTO submission_file (submission_id, file_name, file) VALUES (?, ?, ?)";

        try (PreparedStatement submissionFileStatement = connection.prepareStatement(insertSubmissionFileQuery)) {
            for (SubmissionFile submissionFile : submission.getSubmissionFiles()) {
                submissionFileStatement.setLong(1, submission.getId());
                submissionFileStatement.setString(2, submissionFile.getName());
                submissionFileStatement.setBytes(3, submissionFile.getBytes());

                submissionFileStatement.addBatch();
            }

            submissionFileStatement.executeBatch();
        }
    }

    private void insertCheckerResults(Connection connection, Submission submission) throws SQLException {

        if (submission.getCheckerResults() != null) {
            String insertCheckerResultQuery
                    = "INSERT INTO checker_result (exercise_id, checker_id, submission_id, has_passed, result_description) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement checkerResultStatement = connection.prepareStatement(insertCheckerResultQuery)) {
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


