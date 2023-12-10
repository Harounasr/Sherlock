package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.TestateComment;
import de.ssherlock.persistence.exception.PersistenceNonExistentTestateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public void insertTestate(Testate testate) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public Testate getTestate(long exerciseId, String studentUsername)
            throws PersistenceNonExistentTestateException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Testate> getTestates(long exerciseId) {
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
            statement.setLong(1, exerciseId);
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
    public List<Testate> getTestates(long exerciseId, String tutorUsername) {
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
            statement.setLong(1, exerciseId);
            statement.setString(2, tutorUsername);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                testateList = processSubmissionResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.fine("......");
        }
        return testateList;
    }

    /*private List<Testate> processSubmissionResultSet(ResultSet resultSet) throws SQLException {

        List<Testate> testateList = new ArrayList<>();
        long currentSubmissionId = -1;
        Testate currentTestate = null;


        do {
            long submissionId = resultSet.getLong("submission_id");

            if (currentSubmissionId != submissionId) {
                currentSubmissionId = submissionId;
                currentTestate = new Testate();
                Submission submission = new Submission();
                submission.setId(submissionId);
                currentTestate.setSubmission(submission);

                // Speichern Sie die Werte in Variablen
                int functionalityGrade = resultSet.getInt("functionality_grade");
                int readabilityGrade = resultSet.getInt("readability_grade");

                // Verwenden Sie die Variablen
                currentTestate.setFunctionalityGrade(functionalityGrade);
                currentTestate.setReadabilityGrade(readabilityGrade);
                System.out.println("Functionality Grade: " + functionalityGrade);
                System.out.println("Readability Grade: " + readabilityGrade);


                List<SubmissionFile> fileList = processFileResultSet(resultSet, submissionId);
                currentTestate.getSubmission().setSubmissionFiles(fileList);

                //List<TestateComment> comments = processCommentResultSet(resultSet, submissionId);
                //currentTestate.setComments(comments);

                testateList.add(currentTestate);
            }
        } while(resultSet.next() );

        return testateList;
    }

    private List<SubmissionFile> processFileResultSet(ResultSet resultSet, long submissionId)
            throws SQLException {
        System.out.println(resultSet.getLong("submission_id"));
        List<SubmissionFile> fileList = new ArrayList<>();
        do {
            SubmissionFile file = new SubmissionFile();
            file.setBytes(resultSet.getBytes("file"));
            fileList.add(file);
        } while (resultSet.next() && resultSet.getLong("submission_id") == submissionId);
        return fileList;
    }

    private List<TestateComment> processCommentResultSet(ResultSet resultSet, long submissionId)
            throws SQLException {
        List<TestateComment> comments = new ArrayList<>();
        do {
            TestateComment comment = new TestateComment();
            comment.setFileId(resultSet.getLong("file_id"));
            comment.setLineNumber(resultSet.getInt("line_number"));
            comment.setComment(resultSet.getString("comment"));

            comments.add(comment);
        } while (resultSet.next() && resultSet.getLong("submission_id") == submissionId);
        return comments;
    }
*/

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
        testate.setEvaluator(resultSet.getString("tutor_username"));
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
