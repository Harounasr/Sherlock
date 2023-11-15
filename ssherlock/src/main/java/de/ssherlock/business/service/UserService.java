package de.ssherlock.business.service;

import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.exception.NonExistentUserException;

import de.ssherlock.persistence.repository.CourseRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import de.ssherlock.persistence.util.Mail;
import de.ssherlock.persistence.util.MailContentBuilder;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.Connection;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class UserService {

    private Logger logger;
    private AppSession appSession;
    private ConnectionPoolPsql connectionPoolPsql;
    private Mail mail;

    @Inject
    public UserService(Logger logger, AppSession appSession, ConnectionPoolPsql connectionPoolPsql, Mail mail) {
        this.logger = logger;
        this.appSession = appSession;
        this.connectionPoolPsql = connectionPoolPsql;
        this.mail = mail;
    }

    public User login(LoginInfo loginInfo) throws LoginFailedException {
        Connection connection = connectionPoolPsql.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        User user;
        try {
            user = userRepository.fetchUser(loginInfo.username());
        } catch (NonExistentUserException e) {
            connectionPoolPsql.releaseConnection(connection);
            logger.log(Level.INFO, "Could not find user " + loginInfo.username() + ".");
            throw new LoginFailedException("The user " + loginInfo.username() + " is not registered in the system");
        }
        connectionPoolPsql.releaseConnection(connection);
        if (Objects.equals(loginInfo.password().hash(), user.password().hash())) {
            logger.log(Level.INFO, "Login for user " + loginInfo.username() + " was successful.");
            appSession.setUser(user);
            return user;
        } else {
            logger.log(Level.INFO, "Incorrect password for user " + loginInfo.username() + " .");
            throw new LoginFailedException("The entered password was incorrect");
        }
    }

    public void registerUser(User user) {
        mail.sendMail(user, MailContentBuilder.buildVerificationMail(user));
    }

    public void sendPasswordForgottenEmail(User user) {
        mail.sendMail(user, MailContentBuilder.buildPasswordResetMail(user));
    }
    public void deleteUser(User user) {

    }
    public void updateUser(User user) {

    }

}
