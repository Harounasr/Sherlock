package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.NonExistentUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
            logger.log(Level.INFO, "Attempting to find user with username: " + username);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new User(
                        result.getString("username"),
                        result.getString("username"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        SystemRole.TEACHER,
                        new Password(result.getString("passwordhash"), result.getString("passwordsalt")),
                        result.getString("faculty")
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
