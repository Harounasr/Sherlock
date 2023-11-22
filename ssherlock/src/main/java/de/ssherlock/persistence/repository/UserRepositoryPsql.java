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
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Implementation of UserRepository for PostgreSQL database.
 */
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
    @Override
    public void insertUser(User user) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(User user) throws PersistenceNonExistentUserException  {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(String username) throws PersistenceNonExistentUserException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String username) throws PersistenceNonExistentUserException {
        try {
            String sqlQuery =
                    """
                            SELECT 
                                u.username, u.email, u.firstname, u.lastname, u.systemrole, u.passwordhash,
                                u.passwordsalt, u.facultyname, r.course_name, r.course_role
                            FROM 
                                users u LEFT JOIN participates r ON u.username = r.username
                            WHERE 
                                u.username = ?;
                            """;

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = new User();
                Password password = new Password();
                password.setHash(result.getString("passwordhash"));
                password.setSalt(result.getString("passwordsalt"));
                user.setUsername(username);
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setEmail(result.getString("email"));
                user.setSystemRole(SystemRole.valueOf(result.getString("systemrole")));
                user.setFacultyName(result.getString("facultyname"));
                user.setPassword(password);
                Map<String, CourseRole> courseRoles = new HashMap<>();
                do {
                    String courseName = result.getString("course_name");
                    String courseRole = result.getString("course_role");
                    if (courseName != null && courseRole != null) {
                        courseRoles.put(courseName, CourseRole.valueOf(courseRole));
                    }
                } while (result.next());
                user.setCourseRoles(courseRoles);
                return user;
            } else {
                throw new PersistenceNonExistentUserException("The user with the username " + username + " could not be found in the database.");
            }
        } catch (SQLException e) {
            throw new PersistenceNonExistentUserException("The user with the username " + username + " could not be found in the database.", e);

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return null;
    }

}
