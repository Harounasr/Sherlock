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
                """;

        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    Testate testate = new Testate();
                    Submission submission = new Submission();
                    testate.setLayoutGrade(resultSet.getInt("layout_grade"));
                    testate.setStructureGrade(resultSet.getInt("structure_grade"));
                    testate.setFunctionalityGrade(resultSet.getInt("functionality_grade"));
                    testate.setReadabilityGrade(resultSet.getInt("readability_grade"));
                    testate.setEvaluatorId(resultSet.getLong("tutor_id"));
                    submission.setId(resultSet.getLong("submission_id"));
                    submission.setTutor(resultSet.getString("tutor_username"));
                    submission.setUser(resultSet.getString("student_username"));
                    testate.setSubmission(submission);
                    testateList.add(testate);
                } while (resultSet.next());
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
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            statement.setString(2, tutor.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    Testate testate = new Testate();
                    Submission submission = new Submission();
                    testate.setLayoutGrade(resultSet.getInt("layout_grade"));
                    testate.setStructureGrade(resultSet.getInt("structure_grade"));
                    testate.setFunctionalityGrade(resultSet.getInt("functionality_grade"));
                    testate.setReadabilityGrade(resultSet.getInt("readability_grade"));
                    testate.setEvaluatorId(resultSet.getLong("tutor_id"));
                    submission.setId(resultSet.getLong("submission_id"));
                    submission.setTutor(resultSet.getString("tutor_username"));
                    submission.setUser(resultSet.getString("student_username"));
                    testate.setSubmission(submission);
                    testateList.add(testate);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            logger.fine("......");
        }
        return testateList;
    }
}
