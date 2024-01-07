package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.TestateComment;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentTestateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementation of TestateRepository for PostgreSQL database.
 *
 * @author Haroun Alswedany
 */
public class TestateRepositoryPsql extends RepositoryPsql implements TestateRepository {

    /**
     * Logger instance for logging messages related to TestateRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(TestateRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public TestateRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public void insertTestate(Testate testate) {
        String sqlQuery = """
                          INSERT INTO testate (submission_id, tutor_id, layout_grade, structure_grade, readability_grade, functionality_grade)
                          VALUES (?, ?, ?, ?, ?, ?);
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, testate.getSubmission().getId());
            statement.setLong(2, testate.getEvaluatorId());
            statement.setObject(3, testate.getLayoutGrade(), Types.OTHER);
            statement.setObject(4, testate.getStructureGrade(), Types.OTHER);
            statement.setObject(5, testate.getReadabilityGrade(), Types.OTHER);
            statement.setObject(6, testate.getFunctionalityGrade(), Types.OTHER);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "Insertion of new testate was not possible.");
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Testate getTestate(Exercise exercise, User student)
            throws PersistenceNonExistentTestateException {
        String sqlQuery = """
                          SELECT * FROM testate
                          WHERE submission_id IN (
                          SELECT * FROM submission s
                          WHERE s.student_username = ? AND s.exercise_id = ?
                          );
                          """;
        Testate testate = new Testate();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, student.getUsername());
            statement.setLong(2, exercise.getId());
            ResultSet resultSet = statement.executeQuery();
            testate.setLayoutGrade(resultSet.getInt("layout_grade"));
            testate.setStructureGrade(resultSet.getInt("structure_grade"));
            testate.setFunctionalityGrade(resultSet.getInt("functionality_grade"));
            testate.setReadabilityGrade(resultSet.getInt("readability_grade"));
            testate.setEvaluatorId(resultSet.getLong("tutor_id"));
        } catch (SQLException e) {
            logger.log(Level.INFO, "Testate not available.");
            throw new PersistenceNonExistentTestateException();
        }
        return testate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Testate> getTestates(Exercise exercise) {
        List<Testate> testateList = new ArrayList<>();
        String sqlQuery =
                """
                      SELECT *
                      FROM (
                          SELECT *
                          FROM submission
                          WHERE exercise_id = ?
                      ) s
                      JOIN testate t ON s.id = t.submission_id
                      JOIN submission_file sf ON s.id = sf.submission_id
                      LEFT JOIN testate_comment tc ON sf.id = tc.file_id;
                """;

        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            ResultSet resultSet = statement.executeQuery();
            testateList = processSubmissionResultSet(resultSet);
            boolean x = true;

            while (x) {
                System.out.println(testateList.get(0).getComments().get(0));
                System.out.println(testateList.get(0).getSubmission().getId());
                System.out.println(testateList.get(1).getSubmission().getId());
                System.out.println(testateList.get(0).getFunctionalityGrade());
                System.out.println(testateList.get(1).getFunctionalityGrade());
                System.out.println(testateList.get(0).getReadabilityGrade());
                System.out.println(testateList.get(1).getReadabilityGrade());
                x = false;

            }

        } catch (SQLException e) {

        }
        return testateList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Testate> getTestates(Exercise exercise, User tutor) {
        List<Testate> testateList = new ArrayList<>();
        String sqlQuery =
                """
                    SELECT *
                      FROM (
                          SELECT *
                          FROM submission
                          WHERE s.exercise_id = ? AND s.tutor_username = ?;
                      ) s
                      JOIN testate t ON s.id = t.submission_id
                      JOIN submission_file sf ON s.id = sf.submission_id
                      LEFT JOIN testate_comment tc ON sf.id = tc.file_id;
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            statement.setString(2, tutor.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                testateList = processSubmissionResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.fine("......");
        }
        return testateList;
    }

    private List<Testate> processSubmissionResultSet(ResultSet resultSet) throws SQLException {
        long currentSubmissionId = -1;
        List<Testate> testateList = new ArrayList<>();
        List<SubmissionFile> fileList = new ArrayList<>();
        List<TestateComment> comments = new ArrayList<>();
        Testate currentTestate = new Testate();

        while (resultSet.next()) {
            long submissionId = resultSet.getLong("submission_id");

            if (submissionId != currentSubmissionId) {
                processCurrentTestate(currentSubmissionId, currentTestate, fileList, comments, testateList);

                currentTestate = createNewTestate(submissionId, resultSet);
                currentSubmissionId = submissionId;
            }

            SubmissionFile file = createSubmissionFile(resultSet);
            fileList.add(file);

            TestateComment comment = createTestateComment(resultSet);
            comments.add(comment);
        }

        processCurrentTestate(currentSubmissionId, currentTestate, fileList, comments, testateList);

        return testateList;
    }

    private void processCurrentTestate(
            long currentSubmissionId,
            Testate currentTestate,
            List<SubmissionFile> fileList,
            List<TestateComment> comments,
            List<Testate> testateList) {

        if (currentSubmissionId != -1) {
            currentTestate.getSubmission().setSubmissionFiles(new ArrayList<>(fileList));
            currentTestate.setComments(new ArrayList<>(comments));
            testateList.add(currentTestate);
        }

        fileList.clear();
        comments.clear();
    }

    private Testate createNewTestate(long submissionId, ResultSet resultSet) throws SQLException {
        Testate testate = new Testate();
        Submission submission = new Submission();
        submission.setId(submissionId);
        submission.setTimestamp(resultSet.getTimestamp("timestamp_submission"));
        testate.setSubmission(submission);
        testate.setFunctionalityGrade(resultSet.getInt("functionality_grade"));
        testate.setReadabilityGrade(resultSet.getInt("readability_grade"));
        testate.setEvaluatorId(resultSet.getLong("tutor_username"));
        testate.setStudent(resultSet.getString("student_username"));
        return testate;
    }

    private SubmissionFile createSubmissionFile(ResultSet resultSet) throws SQLException {
        SubmissionFile file = new SubmissionFile();
        file.setBytes(resultSet.getBytes("file"));
        return file;
    }

    private TestateComment createTestateComment(ResultSet resultSet) throws SQLException {
        TestateComment comment = new TestateComment();
        comment.setFileId(resultSet.getLong("file_id"));
        comment.setLineNumber(resultSet.getInt("line_number"));
        comment.setComment(resultSet.getString("comment"));
        // comment.setState(resultSet.getBoolean("is_visible"));
        return comment;
    }
}
