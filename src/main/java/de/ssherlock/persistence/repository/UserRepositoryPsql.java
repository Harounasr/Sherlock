package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Implementation of UserRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
public class UserRepositoryPsql extends RepositoryPsql implements UserRepository {

  /** Logger instance for logging messages related to UserRepositoryPsql. */
  private final SerializableLogger logger = LoggerCreator.get(UserRepositoryPsql.class);

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public UserRepositoryPsql(Connection connection) {
    super(connection);
  }

  /** {@inheritDoc} */
  @Override
  public void insertUser(User user) {}

  /** {@inheritDoc} */
  @Override
  public void updateUser(User user) throws PersistenceNonExistentUserException {
    logger.log(Level.INFO, "1");
    String sqlQuery =
        """
                      UPDATE "user"
                      SET failed_login_attempts = ?
                      WHERE username = ?
                    """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setInt(1, user.getFailedLoginAttempts());
      statement.setString(2, user.getUsername());

      int rowsAffected = statement.executeUpdate();

      if (rowsAffected == 0) {
        throw new PersistenceNonExistentUserException();
      }
    } catch (SQLException e) {
      throw new PersistenceNonExistentUserException();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void deleteUser(String username) throws PersistenceNonExistentUserException {}

  /** {@inheritDoc} */
  @SuppressWarnings("checkstyle:LocalVariableNamingConventions")
  @Override
  public User getUser(String username) throws PersistenceNonExistentUserException {
    String sqlQuery =
        """
                SELECT
                    u.id, u.username, u.email, u.firstname, u.lastname, u.user_role, u.password_hash,
                    u.password_salt, u.faculty, u.failed_login_attempts, p.course_id, p.user_role AS course_role
                FROM
                    "user" u LEFT JOIN participates p ON u.id = p.user_id
                WHERE
                    username = ?;
                """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, username);
      try (ResultSet result = statement.executeQuery()) {
        if (result.next()) {
          User user = new User();
          Password password = new Password();
          password.setHash(result.getString("password_hash"));
          password.setSalt(result.getString("password_salt"));
          user.setUsername(username);
          user.setFirstName(result.getString("firstname"));
          user.setLastName(result.getString("lastname"));
          user.setEmail(result.getString("email"));
          user.setFailedLoginAttempts(result.getInt("failed_login_attempts"));
          user.setSystemRole(SystemRole.valueOf(result.getString("user_role")));
          user.setFacultyName(result.getString("faculty"));
          user.setPassword(password);
          Map<Integer, CourseRole> courseRoles = new HashMap<>();

          do {
            int course_id = result.getInt("course_id");
            String courseRole = result.getString("course_role");
            if (course_id != 0 && courseRole != null) {
              courseRoles.put(course_id, CourseRole.valueOf(courseRole));
            }
          } while (result.next());

          user.setCourseRoles(courseRoles);
          return user;
        } else {
          logger.fine("Table for User is empty");
          throw new PersistenceNonExistentUserException();
        }
      }
    } catch (SQLException e) {
      throw new PersistenceNonExistentUserException(
          "The user with the username " + username + " could not be found in the database.", e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<User> getUsers() {
    return null;
  }
}
