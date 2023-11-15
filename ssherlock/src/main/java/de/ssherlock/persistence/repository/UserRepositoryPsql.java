package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.NonExistentUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepositoryPsql extends RepositoryPsql implements UserRepository {

    private final Logger logger = LoggerCreator.get(UserRepositoryPsql.class);
    public UserRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public User fetchUser(String username) throws NonExistentUserException {
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
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String email = result.getString("email");
                String systemRole = result.getString("systemrole");
                String passwordHash = result.getString("passwordhash");
                String passwordSalt = result.getString("passwordsalt");
                String facultyName = result.getString("facultyname");

                Map<String, CourseRole> courseRoles = new HashMap<>();
                do {
                    String courseName = result.getString("course_name");
                    String courseRole = result.getString("course_role");
                    if (courseName != null && courseRole != null) {
                        courseRoles.put(courseName, CourseRole.valueOf(courseRole));
                    }
                } while (result.next());
                return new User(
                        username,
                        email,
                        firstname,
                        lastname,
                        SystemRole.valueOf(systemRole),
                        new Password(passwordHash, passwordSalt),
                        facultyName,
                        courseRoles
                );
            } else {
                throw new NonExistentUserException("The user with the username " + username + " could not be found in the database.");
            }
        } catch (SQLException e) {
            throw new NonExistentUserException("The user with the username " + username + " could not be found in the database.", e);

        }
    }

    @Override
    public List<User> fetchUsers(Predicate<User> predicate) {
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    private String buildStatementFromPredicate(Predicate<User> predicate) {
        return "";
    }


}
