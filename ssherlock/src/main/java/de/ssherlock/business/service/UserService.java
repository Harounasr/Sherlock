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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UserService class provides functionality for managing users and related operations, such as
 * user authentication, registration, and user account management.
 */
@Named
@Dependent
public class UserService {

    /**
     * Logger instance for logging messages related to UserService.
     */
    private final Logger logger;

    /**
     * AppSession instance for managing user sessions.
     */
    private final AppSession appSession;

    /**
     * Connection pool for managing database connections.
     */
    private final ConnectionPoolPsql connectionPoolPsql;

    /**
     * Mail instance for sending emails related to user actions.
     */
    private final Mail mail;

    /**
     * Constructs a UserService with the specified logger, AppSession, ConnectionPoolPsql, and Mail.
     *
     * @param logger             The logger to be used for logging messages related to UserService.
     * @param appSession         The AppSession instance for managing user sessions.
     * @param connectionPoolPsql The ConnectionPoolPsql instance for managing database connections.
     * @param mail               The Mail instance for sending emails.
     */
    @Inject
    public UserService(Logger logger, AppSession appSession, ConnectionPoolPsql connectionPoolPsql, Mail mail) {
        this.logger = logger;
        this.appSession = appSession;
        this.connectionPoolPsql = connectionPoolPsql;
        this.mail = mail;
    }

    /**
     * Authenticates a user based on the provided login information.
     *
     * @param loginInfo The login information containing the username and hashed password.
     * @return The authenticated user.
     * @throws LoginFailedException If the login fails, either due to a non-existent user or incorrect password.
     */
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

    /**
     * Registers a new user and sends a verification email.
     *
     * @param user The user to be registered.
     */
    public void registerUser(User user) {
        mail.sendMail(user, MailContentBuilder.buildVerificationMail(user));
    }

    /**
     * Sends a password reset email to the user.
     *
     * @param user The user for whom to send the password reset email.
     */
    public void sendPasswordForgottenEmail(User user) {
        mail.sendMail(user, MailContentBuilder.buildPasswordResetMail(user));
    }

    /**
     * Deletes a user account.
     *
     * @param user The user to be deleted.
     */
    public void deleteUser(User user) {
    }

    /**
     * Updates user account information.
     *
     * @param user The user with updated information.
     */
    public void updateUser(User user) {
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    public List<User> getUsers() {
        return null;
    }
}

