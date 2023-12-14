package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.exception.LoginFailedException;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.LoginInfo;
import de.ssherlock.global.transport.Pagination;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
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
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The UserService class provides functionality for managing users and related operations, such as
 * user authentication, registration, and user account management.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class UserService implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * For random byte generation.
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

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
    private final ConnectionPool connectionPool;

    /**
     * Mail instance for sending emails related to user actions.
     */
    private final Mail mail;

    /**
     * Constructs a UserService with the specified logger, AppSession, ConnectionPoolPsql, and Mail.
     *
     * @param logger         The logger to be used for logging messages related to UserService.
     * @param appSession     The AppSession instance for managing user sessions.
     * @param connectionPool The ConnectionPoolPsql instance for managing database connections.
     * @param mail           The Mail instance for sending emails.
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
     * @throws LoginFailedException             If the login fails, either due to a non-existent user or incorrect
     *                                          password.
     * @throws BusinessNonExistentUserException when the user is not registered in the system.
     */
    public User login(LoginInfo loginInfo)
            throws LoginFailedException, BusinessNonExistentUserException {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        User user = new User();
        user.setUsername(loginInfo.getUsername());
        try {
            user = userRepository.getUser(user);
        } catch (PersistenceNonExistentUserException e) {
            connectionPool.releaseConnection(connection);
            logger.log(Level.INFO, "Could not find user " + loginInfo.getUsername() + "");
            throw new BusinessNonExistentUserException(
                    "The user " + loginInfo.getUsername() + " is not registered in the system");
        }
        // uncomment after failedLoginAttemptsCleanUp was implemented
    /*
    if (user.getFailedLoginAttempts() >= 5) {
        throw new LoginFailedException();
    }
       */
        if (user.getSystemRole() == SystemRole.NOT_VERIFIED) {
            throw new LoginFailedException();
        }
        if (Objects.equals(
                user.getPassword(),
                PasswordHashing.hashPassword(
                        loginInfo.getUnhashedPassword(), user.getPassword().getSalt()))) {
            user.setFailedLoginAttempts(0);
            try {
                userRepository.updateUser(user);
            } catch (PersistenceNonExistentUserException e) {
                throw new BusinessNonExistentUserException();
            }
            connectionPool.releaseConnection(connection);
            return user;
        } else {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            try {
                userRepository.updateUser(user);
                connectionPool.releaseConnection(connection);
            } catch (PersistenceNonExistentUserException e) {
                connectionPool.releaseConnection(connection);
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
    @SuppressWarnings("checkstyle:MagicNumber")
    public void registerUser(User user) {
        user.setSystemRole(SystemRole.NOT_VERIFIED);
        String verificationToken = generateEmailVerificationToken();
        user.setVerificationToken(verificationToken);
        Instant now = Instant.now();
        Instant oneWeekLater = now.plus(Duration.ofDays(7));
        user.setExpiryDate(Timestamp.from(oneWeekLater));
        if (mail.sendVerificationMail(user, MailContentBuilder.buildVerificationMail(user))) {
            Connection connection = connectionPool.getConnection();
            UserRepository userRepository =
                    RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
            userRepository.insertUser(user);
            connectionPool.releaseConnection(connection);
        } else {
            Notification notification =
                    new Notification("Email could not be send. Please try again.", NotificationType.ERROR);
            notification.generateUIMessage();
        }
    }

    /**
     * Sends a password reset email to the user.
     *
     * @param user The user for whom to send the password reset email.
     * @throws BusinessNonExistentUserException when the user is not registered in the system.
     */
    public void sendPasswordForgottenEmail(User user) throws BusinessNonExistentUserException {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        try {
            user = userRepository.getUser(user);
            user.setVerificationToken(generateEmailVerificationToken());
            Instant now = Instant.now();
            Instant oneDayLater = now.plus(Duration.ofDays(1));
            user.setExpiryDate(Timestamp.from(oneDayLater));
            userRepository.updateUser(user);
            mail.sendPasswordResetMail(user, MailContentBuilder.buildPasswordResetMail(user));
        } catch (PersistenceNonExistentUserException e) {
            logger.log(Level.INFO, "No user found.");
            throw new BusinessNonExistentUserException();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Deletes a user account.
     *
     * @param user The user to be deleted.
     * @throws BusinessNonExistentUserException when the user is not registered in the system.
     */
    public void deleteUser(User user) throws BusinessNonExistentUserException {}

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
     * @param pagination The pagination.
     * @param course The course.
     * @return A list of all users.
     */
    public List<User> getUsers(Pagination pagination, Course course) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        List<User> users = userRepository.getUsers();
        connectionPool.releaseConnection(connection);

        Stream<User> userStream = users.stream();

        if (!pagination.getSearchString().isEmpty()) {
            userStream = userStream.filter(user -> user.getUsername().contains(pagination.getSearchString()));
        }

        String sortBy = pagination.getSortBy();
        if (!sortBy.isEmpty()) {
            Comparator<User> comparator = switch (sortBy) {
                case "username" -> Comparator.comparing(User::getUsername);
                case "courserole" -> Comparator.comparing(user -> user.getCourseRoles().get(course.getId()).toString());
                default -> (user1, user2) -> 0;
            };
            userStream = pagination.isSortAscending() ? userStream.sorted(comparator) : userStream.sorted(comparator.reversed());
        }

        return userStream.collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all users.
     *
     * @param pagination The pagination.
     * @return A list of all users.
     */
    public List<User> getUsers(Pagination pagination) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        List<User> users = userRepository.getUsers();
        connectionPool.releaseConnection(connection);

        Stream<User> userStream = users.stream();

        if (!pagination.getSearchString().isEmpty()) {
            userStream = userStream.filter(user -> user.getUsername().contains(pagination.getSearchString()));
        }

        String sortBy = pagination.getSortBy();
        if (!sortBy.isEmpty()) {
            Comparator<User> comparator = switch (sortBy) {
                case "username" -> Comparator.comparing(User::getUsername);
                case "systemrole" -> Comparator.comparing(user -> user.getSystemRole().toString());
                default -> (user1, user2) -> 0;
            };
            userStream = pagination.isSortAscending() ? userStream.sorted(comparator) : userStream.sorted(comparator.reversed());
        }

        return userStream.collect(Collectors.toList());
    }



    /**
     * Gets a user by its username.
     *
     * @param user The user to retrieve.
     * @return the user.
     * @throws BusinessNonExistentUserException when the user is not registered in the system.
     */
    public User getUser(User user) throws BusinessNonExistentUserException {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        try {
            User returnedUser = userRepository.getUser(user);
            connectionPool.releaseConnection(connection);
            return returnedUser;
        } catch (PersistenceNonExistentUserException e) {
            connectionPool.releaseConnection(connection);
            throw new BusinessNonExistentUserException();
        }
    }

    /**
     * Updates the role of a user in a specific course.
     *
     * @param user       The user for whom to update the role.
     * @param courseRole The new role for the user in the course.
     * @throws BusinessNonExistentUserException when the user does not exist in the database.
     */
    public void updateCourseRole(User user, CourseRole courseRole)
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

    /**
     * Checks if a username already exists in the database.
     *
     * @param user The user for whom to check.
     * @return true, in case the username exists, false otherwise.
     */
    public boolean userNameExists(User user) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        boolean userExists = userRepository.userNameExists(user);
        connectionPool.releaseConnection(connection);
        return userExists;
    }

    /**
     * Checks if an email already exists in the database.
     *
     * @param user The user for whom to check.
     * @return true, in case the email exists, false otherwise.
     */
    public boolean emailExists(User user) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        boolean emailExists = userRepository.emailExists(user);
        connectionPool.releaseConnection(connection);
        return emailExists;
    }


    /**
     * Verifies a user.
     *
     * @param user The user to verify.
     */
    public void verifyUser(User user) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository =
                RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        userRepository.verifyUser(user);
        connectionPool.releaseConnection(connection);
    }

    /**
     * Resets the password of a user.
     *
     * @param user The user to reset the password for.
     *
     * @return Whether the reset was successfully or not.
     */
    public boolean resetPassword(User user) {
        Connection connection = connectionPool.getConnection();
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, connection);
        if (userRepository.resetPassword(user)) {
            connectionPool.releaseConnection(connection);
            return true;
        } else {
            connectionPool.releaseConnection(connection);
            return false;
        }
    }

}
