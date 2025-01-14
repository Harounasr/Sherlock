package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Implementation of UserRepository for PostgreSQL database.
 *
 * @author Leon Höfling
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class UserRepositoryPsql extends RepositoryPsql implements UserRepository {

    /**
     * Logger instance for logging messages related to UserRepositoryPsql.
     */
    private final SerializableLogger logger = LoggerCreator.get(UserRepositoryPsql.class);

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The database connection.
     */
    public UserRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public boolean insertUser(User user) {
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
            return true;
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not insert user." + e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public void updateUser(User user) throws PersistenceNonExistentUserException {
        String sqlQuery =
                """
                  UPDATE "user"
                  SET
                   failed_login_attempts = COALESCE(?, failed_login_attempts),
                   user_role = COALESCE(CAST(? AS system_role), user_role),
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
            statement.setString(4, user.getPassword() != null ? user.getPassword().getHash() : null);
            statement.setString(5, user.getPassword() != null ? user.getPassword().getSalt() : null);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(User user) {
        logger.log(Level.INFO, "repo: " + user.getUsername());
        String sqlQuery = "DELETE FROM \"user\" WHERE username = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setString(1, user.getUsername());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Deletion successful
                logger.log(
                        Level.INFO, "User with username '" + user.getUsername() + "' deleted successfully.");
            } else {
                // No user found with the given username
                logger.log(
                        Level.INFO,
                        "No user found with username '" + user.getUsername() + "'. Deletion failed.");
            }
        } catch (SQLException e) {
            logger.log(
                    Level.INFO, "Could not delete user with username '" + user.getUsername() + "'. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
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
                    user.setId(result.getLong("id"));
                    user.setUsername(result.getString("username"));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        String sqlQuery =
                """
                SELECT username FROM "user";
                """;
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    User user = new User();
                    user.setUsername(result.getString("username"));
                    try {
                        user = getUser(user);
                    } catch (PersistenceNonExistentUserException e) {
                        logger.severe("User with username " + user.getUsername() + " does not exist anymore.");
                    }
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsersForCourse(Course course) throws PersistenceNonExistentCourseException {
        String sqlQuery =
                """
                SELECT
                    u.username
                FROM
                    participates p
                JOIN
                    "user" u ON p.user_id = u.id
                WHERE
                    p.course_id = ?;
                """;
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, course.getId());
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    User user = new User();
                    user.setUsername(result.getString("username"));
                    try {
                        user = getUser(user);
                    } catch (PersistenceNonExistentUserException e) {
                        logger.severe("User with username " + user.getUsername() + " does not exist anymore.");
                    }
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceNonExistentCourseException("The course does not exist.");
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     **/
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public boolean resetPassword(User user) {
        String sqlQuery = """
                          UPDATE "user"
                          SET password_hash = ?,
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCourseRole(User user, Course course, CourseRole courseRole) {
        if (courseRole == CourseRole.NONE) {
            String sqlQuery = """
                              DELETE FROM participates
                              WHERE user_id = ? AND course_id = ?;
                              """;
            try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
                statement.setLong(1, user.getId());
                statement.setLong(2, course.getId());
                statement.executeUpdate();
                return;
            } catch (SQLException e) {
                logger.log(Level.INFO, "Error while updating user.");
                throw new RuntimeException(e);
            }
        }
        String sqlQuery = """
                          INSERT INTO participates (user_id, course_id, user_role)
                          VALUES (?, ?, ?::course_role)
                          ON CONFLICT (user_id, course_id) DO UPDATE
                          SET user_role = EXCLUDED.user_role;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, course.getId());
            statement.setString(3, courseRole.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "Error while updating user.");
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUnverifiedUsers() {
        String sqlQuery = """
                          DELETE FROM "user"
                          WHERE expiry_date < (SELECT CURRENT_TIMESTAMP)
                          AND user_role = 'NOT_REGISTERED'
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not delete user.");
        }
    }

    /**
     * {@inheritDoc}
     */

    public void resetPasswordAttempts() {
        String sqlQuery = """
                          UPDATE "user"
                          SET failed_login_attempts = 0;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not Reset Password Attempts.");
        }
    }

    /**
     * {@inheritDoc}
     */
    public HashMap<Exercise, List<User>> getDataForReminderMail() {
        HashMap<Exercise, List<User>> result = new HashMap<>();
        String sqlQuery = """
                          SELECT
                              e.id,
                              e.name,
                              e.obligatory_deadline,
                              u.email
                          FROM
                              exercise e
                                  JOIN
                              course c ON e.course_id = c.id
                                  JOIN
                              participates p ON c.id = p.course_id
                                  JOIN
                              "user" u ON p.user_id = u.id
                          WHERE obligatory_deadline < NOW() + INTERVAL '2 DAYS' AND reminder_mail_sent = FALSE;
                          """;
        try (PreparedStatement statement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    User user = new User();
                    user.setEmail(resultSet.getString("email"));

                    Exercise exercise = new Exercise();
                    exercise.setId(resultSet.getLong("id"));
                    exercise.setName(resultSet.getString("name"));
                    exercise.setObligatoryDeadline(resultSet.getTimestamp("obligatory_deadline"));

                    result.computeIfAbsent(exercise, k -> new ArrayList<>()).add(user);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "Could not get data for reminder mail.");
        }
        return result;
    }
}
