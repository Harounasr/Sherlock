package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Faculty;
import de.ssherlock.persistence.exception.PersistenceNonExistentFacultyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementation of FacultyRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class FacultyRepositoryPsql extends RepositoryPsql implements FacultyRepository {

  /** Logger instance for logging messages related to FacultyRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(FacultyRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public FacultyRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertFaculty(Faculty faculty) {}

  /** {@inheritDoc} */
  @Override
  public void deleteFaculty(Faculty faculty) throws PersistenceNonExistentFacultyException {}

  /** {@inheritDoc} */
  @Override
  public List<Faculty> getFaculties() {
    List<Faculty> faculties = new ArrayList<>();
    String sqlQuery = "SELECT * FROM faculty;";
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          if (!resultSet.getString("name").equals("NONE")) {
            Faculty faculty = new Faculty();
            faculty.setName(resultSet.getString("name"));
            faculties.add(faculty);
          }
        }
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, "There are no faculties in the database.");
    }
    return faculties;
  }
}
