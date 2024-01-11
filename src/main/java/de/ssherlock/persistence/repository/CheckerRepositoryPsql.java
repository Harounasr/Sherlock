package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * of CheckerRepository for PostgreSQL database.
 *
 * @author Lennart Hohls
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {

    /**
     * Logger instance for logging messages related to CheckerRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(CheckerRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public CheckerRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * inserts a checker.
     *
     * @param checker The Checker entity to be inserted.
     */
    @Override
    public void insertChecker(Checker checker) {
        String sqlQuery =
                """
                INSERT INTO checker
                (exercise_id,is_visible,is_required,parameter_1,parameter_2,type)
                VALUES (?,?,?,?,?,CAST(? as checker_type));
                """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, checker.getExerciseId());
            statement.setBoolean(2, checker.isVisible());
            statement.setBoolean(3, checker.isMandatory());
            statement.setString(4, checker.getParameterOne());
            statement.setString(5, checker.getParameterTwo());
            statement.setString(6, checker.getCheckerType().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            logger.log(Level.INFO, "could not insert checker");
        }
    }

    /**
     * Updates a checker.
     *
     * @param checker The Checker entity to be updated.
     * @throws PersistenceNonExistentCheckerException if the checker was not found
     */
    @Override
    public void updateChecker(Checker checker) throws PersistenceNonExistentCheckerException {
        String sqlQuery =
                """
                UPDATE checker
                SET
                exercise_id = COALESCE(?,exercise_id),
                is_visible = COALESCE(?,is_visible),
                is_required = COALESCE(?,is_required),
                parameter_1 = COALESCE(?,parameter_1),
                parameter_2 = COALESCE(?,parameter_2),
                type = COALESCE(CAST(? as checker_type),type)
                WHERE id = ?
                """;
        Long exerciseID = checker.getExerciseId();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setObject(1, exerciseID != 0 ? checker.getExerciseId() : null);
            statement.setBoolean(2, checker.isVisible());
            statement.setBoolean(3, checker.isMandatory());
            statement.setString(4, checker.getParameterOne());
            statement.setString(5, checker.getParameterTwo());
            statement.setString(
                    6, checker.getCheckerType() != null ? checker.getCheckerType().toString() : null);
            statement.setLong(7, checker.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.log(Level.INFO, "not update nada");
                throw new PersistenceNonExistentCheckerException();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            throw new PersistenceNonExistentCheckerException();
        }
    }

    /**
     * Deletes a checker.
     *
     * @param checker The Checker entity to be deleted.
     * @throws PersistenceNonExistentCheckerException if the checker was not found
     */
    @Override
    public void deleteChecker(Checker checker) throws PersistenceNonExistentCheckerException {
        logger.log(Level.INFO, "repo: " + checker.getId());
        String sqlQuery = "DELETE FROM checker WHERE id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, checker.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Deletion successful
                logger.log(Level.INFO, "Checker with id '" + checker.getId() + "' deleted successfully.");
            } else {
                // No user found with the given username
                logger.log(
                        Level.INFO,
                        "No checker found with checkerID '" + checker.getId() + "'. Deletion failed.");
                throw new PersistenceNonExistentCheckerException();
            }

        } catch (SQLException e) {
            logger.log(
                    Level.INFO, "Could not delete checker with checkerID '" + checker.getId() + "'. " + e);
            throw new PersistenceNonExistentCheckerException();
        }
    }

    /**
     * Gets a specific checker.
     *
     * @param checker The Checker entity to be fetched.
     * @return the checker DTO
     * @throws PersistenceNonExistentCheckerException if the checker was not found
     */
    @Override
    public Checker getChecker(Checker checker) throws PersistenceNonExistentCheckerException {
        String sqlQuery = "SELECT * FROM checker where id=?;";

        Checker returnChecker = new Checker();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, checker.getId());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                returnChecker.setId(result.getInt("id"));
                returnChecker.setExerciseId(result.getInt("exercise_id"));
                returnChecker.setMandatory(result.getBoolean("is_required"));
                returnChecker.setParameterOne(result.getString("parameter_1"));
                returnChecker.setParameterTwo(result.getString("parameter_2"));
                returnChecker.setVisible(result.getBoolean("is_visible"));
                returnChecker.setCheckerType(CheckerType.valueOf(result.getString("checker_type")));
            } else {
                throw new PersistenceNonExistentCheckerException();
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "sql exception checkerRep");
            throw new RuntimeException(e);
        }
        return returnChecker;
    }

    /**
     * Gets the list of all available checkers.
     *
     * @return list of checkers.
     * @throws PersistenceNonExistentCheckerException if no checkers were found.
     */
    @Override
    public List<Checker> getCheckers() throws PersistenceNonExistentCheckerException {
        String sqlQuery = "SELECT * FROM checker;";
        List<Checker> allChecker = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Checker checker = new Checker();
                checker.setId(result.getInt("id"));
                checker.setExerciseId(result.getInt("exercise_id"));
                checker.setMandatory(result.getBoolean("is_required"));
                checker.setParameterOne(result.getString("parameter_1"));
                checker.setParameterTwo(result.getString("parameter_2"));
                checker.setVisible(result.getBoolean("is_visible"));
                checker.setCheckerType(CheckerType.valueOf(result.getString("type")));
                allChecker.add(checker);
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "sql exception checkerRep");
            throw new PersistenceNonExistentCheckerException();
        }
        return allChecker;
    }

    /**
     * Getter for list of checkers with exercise.
     *
     * @param exercise the exercise
     * @return list of checkers
     */
    @Override
    public List<Checker> getCheckersForExercise(Exercise exercise) {
        String sqlQuery = "SELECT * FROM checker WHERE exercise_id = ?;";
        List<Checker> allChecker = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, exercise.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Checker checker = new Checker();
                checker.setId(result.getInt("id"));
                checker.setExerciseId(result.getInt("exercise_id"));
                checker.setMandatory(result.getBoolean("is_required"));
                checker.setParameterOne(result.getString("parameter_1"));
                checker.setParameterTwo(result.getString("parameter_2"));
                checker.setVisible(result.getBoolean("is_visible"));
                checker.setCheckerType(CheckerType.valueOf(result.getString("type")));
                allChecker.add(checker);
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "sql exception checkerRep");
            throw new RuntimeException(e);
        }
        return allChecker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CheckerResult> getCheckerResultsForSubmission(Submission submission) {
        List<CheckerResult> checkerResults = new ArrayList<>();
        String sqlQuery = """
                          SELECT cr.has_passed,
                                 chk.parameter_1,
                                 chk.parameter_2,
                                 chk.type
                          FROM checker_result cr
                          JOIN checker chk ON cr.checker_id = chk.id AND cr.exercise_id = chk.exercise_id
                          WHERE cr.submission_id = ?;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, submission.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    Checker checker = new Checker();
                    checker.setParameterOne(resultSet.getString("parameter_1"));
                    checker.setParameterTwo(resultSet.getString("parameter_2"));
                    checker.setCheckerType(CheckerType.valueOf(resultSet.getString("type")));
                    CheckerResult checkerResult = new CheckerResult();
                    checkerResult.setPassed(resultSet.getBoolean("has_passed"));
                    checkerResult.setChecker(checker);
                    checkerResults.add(checkerResult);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not load checkerresults.");
            throw new RuntimeException();
        }
        return checkerResults;
    }
}
