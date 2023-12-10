package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.UserRepository;
import de.ssherlock.persistence.util.Mail;
import de.ssherlock.persistence.util.MailContentBuilder;
import de.ssherlock.persistence.util.MailType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * The UserService class provides functionality for managing users and related operations, such as
 * user authentication, registration, and user account management.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class UserService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** For random byte generation. */
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  /** Logger instance for logging messages related to UserService. */
  private final SerializableLogger logger;

  /** AppSession instance for managing user sessions. */
  private final AppSession appSession;

  /** Connection pool for managing database connections. */
  private final ConnectionPool connectionPool;

  /** Mail instance for sending emails related to user actions. */
  private final Mail mail;

  /**
   * Constructs a UserService with the specified logger, AppSession, ConnectionPoolPsql, and Mail.
   *
   * @param logger The logger to be used for logging messages related to UserService.
   * @param appSession The AppSession instance for managing user sessions.
   * @param connectionPool The ConnectionPoolPsql instance for managing database connections.
   * @param mail The Mail instance for sending emails.
   */
  @Inject
  public UserService(
      SerializableLogger logger, AppSession appSession, ConnectionPool connectionPool, Mail mail) {
    this.logger = logger;
    this.appSession = appSession;
    this.connectionPool = connectionPool;
    this.mail = mail;
  }

  /**
   * Authenticates a user based on the provided login information.
   *
   * @param loginInfo The login information containing the username and hashed password.
   * @return The authenticated user.
   * @throws LoginFailedException If the login fails, either due to a non-existent user or incorrect
   *     password.
   * @throws BusinessNonExistentUserException when the user is not registered in the system.
   */
  public User login(LoginInfo loginInfo)
      throws LoginFailedException, BusinessNonExistentUserException {
    Connection connection = connectionPool.getConnection();
    UserRepository userRepository =
        RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
    User user;
    try {
      user = userRepository.getUser(loginInfo.getUsername());
    } catch (PersistenceNonExistentUserException e) {
      connectionPool.releaseConnection(connection);
      logger.log(Level.INFO, "Could not find user " + loginInfo.getUsername() + "");
      throw new BusinessNonExistentUserException(
          "The user " + loginInfo.getUsername() + " is not registered in the system");
    }
    connectionPool.releaseConnection(connection);
    // uncomment after failedLoginAttemptsCleanUp was implemented
    /*
    if (user.getFailedLoginAttempts() >= 5) {
        throw new LoginFailedException();
    }
       */
    if (Objects.equals(
        user.getPassword().getHash(),
        PasswordHashing.getHashedPassword(
            loginInfo.getUnhashedPassword(), user.getPassword().getSalt()))) {
      user.setFailedLoginAttempts(0);
      try {
        userRepository.updateUser(user);
      } catch (PersistenceNonExistentUserException e) {
        logger.log(Level.INFO, "Throwd");
        throw new BusinessNonExistentUserException();
      }
      return user;
    } else {
      user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
      try {
        userRepository.updateUser(user);
      } catch (PersistenceNonExistentUserException e) {
        throw new BusinessNonExistentUserException();
      }
      throw new LoginFailedException(
          "The password for " + loginInfo.getUsername() + " was not correct");
    }
  }

  /**
   * Registers a new user and sends a verification email.
   *
   * @param user The user to be registered.
   */
  public void registerUser(User user) {
    mail.sendMail(
        user,
        MailContentBuilder.buildVerificationMail(user, generateEmailVerificationToken()),
        MailType.VERIFICATION);
  }

  /**
   * Sends a password reset email to the user.
   *
   * @param username The username for whom to send the password reset email.
   * @throws BusinessNonExistentUserException when the user is not registered in the system.
   */
  public void sendPasswordForgottenEmail(String username) throws BusinessNonExistentUserException {
    Connection connection = connectionPool.getConnection();
    UserRepository userRepository =
        RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
    User user;
    try {
      user = userRepository.getUser(username);
    } catch (PersistenceNonExistentUserException e) {
      throw new BusinessNonExistentUserException();
    }
    mail.sendMail(user, MailContentBuilder.buildPasswordResetMail(user), MailType.PASSWORD);
  }

  /**
   * Deletes a user account.
   *
   * @param username The user to be deleted.
   * @throws BusinessNonExistentUserException when the user is not registered in the system.
   */
  public void deleteUser(String username) throws BusinessNonExistentUserException {}

  /**
   * Updates user account information.
   *
   * @param user The user with updated information.
   * @throws BusinessNonExistentUserException when the user is not registered in the system.
   */
  public void updateUser(User user) throws BusinessNonExistentUserException {}

  /**
   * Retrieves a list of all users.
   *
   * @return A list of all users.
   */
  public List<User> getUsers() {
    return null;
  }

  /**
   * Gets a user by its username.
   *
   * @param username The username of the user.
   * @return the user.
   * @throws BusinessNonExistentUserException when the user is not registered in the system.
   */
  public User getUser(String username) throws BusinessNonExistentUserException {
    Connection connection = connectionPool.getConnection();
    UserRepository userRepository =
        RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
    try {
      return userRepository.getUser(username);
    } catch (PersistenceNonExistentUserException e) {
      throw new BusinessNonExistentUserException();
    }
  }

  /**
   * Updates the role of a user in a specific course.
   *
   * @param username The user for whom to update the role.
   * @param courseRole The new role for the user in the course.
   * @throws BusinessNonExistentUserException when the user does not exist in the database.
   */
  public void updateCourseRole(String username, CourseRole courseRole)
      throws BusinessNonExistentUserException {}

  /**
   * Generates a user verification token.
   *
   * @return The verification token.
   */
  @SuppressWarnings("checkstyle:MagicNumber")
  private static String generateEmailVerificationToken() {
    byte[] tokenBytes = new byte[32];
    SECURE_RANDOM.nextBytes(tokenBytes);

    BigInteger tokenNumber = new BigInteger(1, tokenBytes);
    String token = tokenNumber.toString(16);

    while (token.length() < 32) {
      token = "0" + token;
    }

    return token;
  }
}