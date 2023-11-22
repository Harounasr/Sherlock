package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;

import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import de.ssherlock.persistence.util.Mail;
import de.ssherlock.persistence.util.MailContentBuilder;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * The UserService class provides functionality for managing users and related operations, such as
 * user authentication, registration, and user account management.
 */
@Named
@Dependent
public class UserService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to UserService.
     */
    private final SerializableLogger logger;

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
    public UserService(SerializableLogger logger, AppSession appSession, ConnectionPoolPsql connectionPoolPsql, Mail mail) {
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
            user = userRepository.fetchUser(loginInfo.getUsername());
        } catch (PersistenceNonExistentUserException e) {
            connectionPoolPsql.releaseConnection(connection);
            logger.log(Level.INFO, "Could not find user " + loginInfo.getUsername() + ".");
            throw new LoginFailedException("The user " + loginInfo.getUsername() + " is not registered in the system");
        }
        connectionPoolPsql.releaseConnection(connection);
        if (Objects.equals(user.getPassword().getHash(), PasswordHashing.getHashedPassword(loginInfo.getUnhashedPassword(), user.getPassword().getSalt()))) {
            return user;
        } else {
            throw new LoginFailedException("The password for " + loginInfo.getUsername() + " was not correct");
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
     * @param username The username for whom to send the password reset email.
     */
    public void sendPasswordForgottenEmail(String username) {
        Connection connection = connectionPoolPsql.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        User user;
        try {
            user = userRepository.fetchUser(username);
        } catch (PersistenceNonExistentUserException e) {
            throw new RuntimeException(e);
        }
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

    public User fetchUserByUsername(String username) throws BusinessNonExistentUserException {
        Connection connection = connectionPoolPsql.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        try {
            return userRepository.fetchUser(username);
        } catch (PersistenceNonExistentUserException e) {
            throw new BusinessNonExistentUserException();
        }
    }
}

