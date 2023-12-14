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
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
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
  @SuppressWarnings("checkstyle:MagicNumber")
  @Override
  public void insertUser(User user) {
    String sqlQuery =
        """
              INSERT INTO "user" (username, email, firstname, lastname, faculty, password_hash, password_salt, user_role, token, expiry_date)
              VALUES (?, ?, ?, ?, ?, ?, ?, 'NOT_REGISTERED', ?, ?);
              """;

    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getFirstName());
      statement.setString(4, user.getLastName());
      statement.setString(5, user.getFacultyName());
      statement.setString(6, user.getPassword().getHash());
      statement.setString(7, user.getPassword().getSalt());
      statement.setString(8, user.getVerificationToken());
      statement.setTimestamp(9, user.getExpiryDate());
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.INFO, "Could not insert user." + e);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("checkstyle:MagicNumber")
  @Override
  public void updateUser(User user) throws PersistenceNonExistentUserException {
    String sqlQuery =
        """
                      UPDATE "user"
                      SET
                       failed_login_attempts = COALESCE(?, failed_login_attempts),
                       user_role = CAST(? AS system_role),
                       faculty = COALESCE(?, faculty),
                       password_hash = COALESCE(?, password_hash),
                       password_salt = coalesce(?, password_salt),
                       token = coalesce(?, token)
                      WHERE username = ?;
                    """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setInt(1, user.getFailedLoginAttempts());
      statement.setString(2, user.getSystemRole() != null ? user.getSystemRole().toString() : null);
      statement.setString(3, user.getFacultyName());
      statement.setString(4, user.getPassword().getHash());
      statement.setString(5, user.getPassword().getSalt());
      statement.setString(6, user.getVerificationToken());
      statement.setString(7, user.getUsername());

      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 0) {
        throw new PersistenceNonExistentUserException();
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, e.toString());
      throw new PersistenceNonExistentUserException();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void deleteUser(User user) throws PersistenceNonExistentUserException {}

  /** {@inheritDoc} */
  @Override
  public User getUser(User user) throws PersistenceNonExistentUserException {
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
      statement.setString(1, user.getUsername());
      try (ResultSet result = statement.executeQuery()) {
        if (result.next()) {
          Password password = new Password();
          password.setHash(result.getString("password_hash"));
          password.setSalt(result.getString("password_salt"));
          user.setFirstName(result.getString("firstname"));
          user.setLastName(result.getString("lastname"));
          user.setEmail(result.getString("email"));
          user.setFailedLoginAttempts(result.getInt("failed_login_attempts"));
          user.setSystemRole(SystemRole.valueOf(result.getString("user_role")));
          user.setFacultyName(result.getString("faculty"));
          user.setPassword(password);
          Map<Long, CourseRole> courseRoles = new HashMap<>();

          do {
            long courseId = result.getLong("course_id");
            String courseRole = result.getString("course_role");
            if (courseId != 0 && courseRole != null) {
              courseRoles.put(courseId, CourseRole.valueOf(courseRole));
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
          "The user with the username "
              + user.getUsername()
              + " could not be found in the database.",
          e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<User> getUsers() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean userNameExists(User user) {
    String sqlQuery = "SELECT COUNT(*) FROM \"user\" WHERE username = ?;";

    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, user.getUsername());
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int count = resultSet.getInt(1);
          return count > 0;
        }
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, "Error while executing sql query.");
      throw new RuntimeException(e);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean emailExists(User user) {
    String sqlQuery = "SELECT COUNT(*) FROM \"user\" WHERE email = ?;";

    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, user.getEmail());
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int count = resultSet.getInt(1);
          return count > 0;
        }
      }
    } catch (SQLException e) {
      logger.log(Level.INFO, "Error while executing sql query.");
      throw new RuntimeException(e);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void verifyUser(User user) {
    String sqlQuery =
        """
                        UPDATE "user"
                              SET user_role = 'REGISTERED'
                              WHERE token = ?
                              AND expiry_date > NOW();
                        """;
    try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
      statement.setString(1, user.getVerificationToken());
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.INFO, "Error while executing sql query.");
      throw new RuntimeException(e);
    }
  }

  /** {@inheritDoc} **/
  @Override
    public boolean resetPassword(User user) {
      String sqlQuery = """
                        UPDATE "user"
                        SET 
                        password_hash = ?,
                        password_salt = ?
                        WHERE token = ?
                        AND expiry_date > NOW();
                        """;
      try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
          statement.setString(1, user.getPassword().getHash());
          statement.setString(2, user.getPassword().getSalt());
          statement.setString(3, user.getVerificationToken());
          statement.executeUpdate();
          return true;
      } catch (SQLException e) {
          logger.log(Level.INFO, "Error while updating user.");
          throw new RuntimeException(e);
      }
  }
}
