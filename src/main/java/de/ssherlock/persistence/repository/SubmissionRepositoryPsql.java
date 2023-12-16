package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of SubmissionRepository for PostgreSQL database.
 *
 * @author Victor Vollmann
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
    public void insertSubmission(Submission submission) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public Submission getSubmission(Submission submission) {
        return null;
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
        String sqlQuery =
                """
                SELECT
                    s.id,
                    s.timestamp_submission,
                    s.tutor_username,
                    GROUP_CONCAT(f.file_name SEPARATOR ';') AS file_names,
                    GROUP_CONCAT(f.file SEPARATOR ';') AS files,
                    GROUP_CONCAT(CONCAT_WS(',', cr.checker_id, cr.has_passed, cr.result_description) SEPARATOR ';') AS checker_results
                FROM
                    submission s
                LEFT JOIN submission_file f ON s.id = f.submission_id
                LEFT JOIN checker_result cr ON s.id = cr.submission_id
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
                    submission.setSubmissionFiles(processSubmissionFiles(result));
                    submission.setCheckerResults(processCheckerResults(result));
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
        String sqlQuery =
                """
                SELECT
                    s.id,
                    s.timestamp_submission,
                    s.student_username,
                    GROUP_CONCAT(f.file_name SEPARATOR ';') AS file_names,
                    GROUP_CONCAT(f.file SEPARATOR ';') AS files,
                    GROUP_CONCAT(CONCAT_WS(',', cr.checker_id, cr.has_passed, cr.result_description) SEPARATOR ';') AS checker_results
                FROM
                    submission s
                LEFT JOIN submission_file f ON s.id = f.submission_id
                LEFT JOIN checker_result cr ON s.id = cr.submission_id
                WHERE
                    s.tutor_username = ? AND s.exercise_id = ?
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
                    submission.setSubmissionFiles(processSubmissionFiles(result));
                    submission.setCheckerResults(processCheckerResults(result));
                    submissions.add(submission);
                }
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
        return submissions;
    }

    private List<SubmissionFile> processSubmissionFiles(ResultSet result) throws SQLException {
        String[] fileNames = result.getString("file_names").split(";");
        String[] files = result.getString("files").split(";");
        List<SubmissionFile> submissionFiles = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            SubmissionFile file = new SubmissionFile();
            file.setName(fileNames[i]);
            file.setBytes(files[i].getBytes());
            submissionFiles.add(file);
        }
        return submissionFiles;
    }

    private List<CheckerResult> processCheckerResults(ResultSet result) throws SQLException {
        String[] checkerResultsData = result.getString("checker_results").split(";");
        List<CheckerResult> checkerResults = new ArrayList<>();
        for (String checkerResult : checkerResultsData) {
            String[] parts = checkerResult.split(",");
            CheckerResult cr = new CheckerResult();
            Checker checker = new Checker();
            checker.setId(Long.parseLong(parts[0]));
            cr.setChecker(checker);
            cr.setPassed(Boolean.parseBoolean(parts[1]));
            cr.setStackTrace(parts[2]);
            checkerResults.add(cr);
        }
        return checkerResults;
    }
}
