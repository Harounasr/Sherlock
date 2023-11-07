package de.ssherlock.business.service;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.exception.NonExistentUserException;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class UserService {

    private static final Logger logger = LoggerCreator.get(UserService.class);

    public UserService() {

    }

    public User login(LoginInfo loginInfo) {
        Connection connection = ConnectionPoolPsql.getInstance().getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        User user = userRepository.fetchUser(loginInfo.username());
        if (loginInfo.password().hash() == user.password().hash()) {
            logger.log(Level.INFO, "login successful!");
            return user;
        } else {
            logger.log(Level.WARNING, "login failed!");
            return null;
        }
    }

    public void addUser() {

    }
    public void removeUser() {

    }
    public void changePassword() {

    }
    public void changeSystemRole() {

    }
}
