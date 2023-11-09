package de.ssherlock.business.service;

import de.ssherlock.business.exception.LoginFailedException;
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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class UserService {

    private final Logger logger = LoggerCreator.get(UserService.class);

    public UserService() {

    }

    public User login(LoginInfo loginInfo) throws LoginFailedException {
        Connection connection = ConnectionPoolPsql.getInstance().getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        User user;
        try {
            user = userRepository.fetchUser(loginInfo.username());
        } catch (NonExistentUserException e) {
            throw new LoginFailedException("The user " + loginInfo.username() + " is not registered in the system");
        }
        if (Objects.equals(loginInfo.password().hash(), user.password().hash())) {
            logger.log(Level.INFO, "LOGIN for user " + loginInfo.username() + " was successful.");
            return user;
        } else {
            throw new LoginFailedException("The entered password was incorrect");
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
    public void verifyLogin(LoginInfo loginInfo) {

    }
}
