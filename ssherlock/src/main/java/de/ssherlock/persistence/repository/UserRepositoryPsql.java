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

    private static final Logger logger = LoggerCreator.get(UserRepositoryPsql.class);
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
    public User fetchUser(String username) {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT user FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            logger.log(Level.INFO, "Statement executed with result: " + result);
            User user = new User(
                    result.getString("username"),
                    result.getString("username"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    SystemRole.TEACHER,
                    new Password(result.getString("passwordHash"), result.getString("passwordSalt")),
                    result.getString("faculty")
            );
            return user;

        } catch (SQLException e) {
            throw new NonExistentUserException("User does not exist. ", e);
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
}
