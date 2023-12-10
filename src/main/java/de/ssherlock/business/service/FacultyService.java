package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentFacultyException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Faculty;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.repository.FacultyRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * The FacultyService class provides functionality for managing faculties and related operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class FacultyService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to FacultyService. */
  private final SerializableLogger logger;

  /** ConnectionPoolPsql instance for getting a database connection. */
  private final ConnectionPool connectionPool;

  /**
   * Constructs a FacultyService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to FacultyService.
   * @param connectionPool The connection pool.
   */
  @Inject
  public FacultyService(SerializableLogger logger, ConnectionPool connectionPool) {
    this.logger = logger;
    this.connectionPool = connectionPool;
  }

  /**
   * Retrieves a list of all faculties.
   *
   * @return A list of all faculties.
   */
  public List<Faculty> getFaculties() {
    Connection connection = connectionPool.getConnection();
    FacultyRepository facultyRepository =
        RepositoryFactory.getFacultyRepository(RepositoryType.POSTGRESQL, connection);
    List<Faculty> faculties = facultyRepository.getFaculties();
    connectionPool.releaseConnection(connection);
    return faculties;
  }

  /**
   * Adds a new Faculty.
   *
   * @param faculty The faculty to add.
   */
  public void addFaculty(Faculty faculty) {}

  /**
   * Checks whether a faculty already exists in the database.
   *
   * @param facultyName The faculty name.
   * @return true if the faculty exists.
   */
  public boolean facultyExists(String facultyName) {
    return false;
  }

  /**
   * Removes an existing faculty.
   *
   * @param facultyName The faculty to remove.
   * @throws BusinessNonExistentFacultyException when the faculty does not exist in the database.
   */
  public void removeFaculty(String facultyName) throws BusinessNonExistentFacultyException {}
}
