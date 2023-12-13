package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.persistence.exception.PersistenceNonExistentCheckerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementation of CheckerRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {

  /** Logger instance for logging messages related to CheckerRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(CheckerRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public CheckerRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertChecker(Checker checker) {
    String sqlQuery =
        """
              INSERT INTO checker
              (id,exercise_id,is_visible,is_required,parameter_1,parameter_2,type)
              VALUES (?,?,?,?,?,?,CAST(? as checker_type));
              """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setLong(1, checker.getId());
      statement.setLong(2, checker.getExerciseId());
      statement.setBoolean(3, checker.isVisible());
      statement.setBoolean(4, checker.isMandatory());
      statement.setString(5, checker.getParameterOne());
      statement.setString(6, checker.getParameterTwo());
      statement.setString(7, checker.getCheckerType().toString());
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.INFO, e.getMessage());
      logger.log(Level.INFO, "could not insert checker");
    }
  }

  /** {@inheritDoc} */
  @Override
  public void updateChecker(Checker checker) throws PersistenceNonExistentCheckerException {}

  /** {@inheritDoc} */
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
            Level.INFO, "No user found with username '" + checker.getId() + "'. Deletion failed.");
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, "Could not delete user with username '" + checker.getId() + "'. " + e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public Checker getChecker(Checker checker) throws PersistenceNonExistentCheckerException {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public List<Checker> getCheckers() {
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
        allChecker.add(checker);
      }

    } catch (SQLException e) {
      logger.log(Level.INFO, "sql exception checkerRep");
      throw new RuntimeException(e);
    }
    return allChecker;
  }
}
