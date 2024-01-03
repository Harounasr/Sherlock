package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import de.ssherlock.persistence.exception.PersistenceNonExistentUserException;

import java.util.List;

/**
 * Interface for interacting with a repository of User entities in the database.
 *
 * @author Leon Höfling
 */
public interface UserRepository {

    /**
     * Inserts a User entity into the database.
     *
     * @param user The User entity to be inserted.
     */
    void insertUser(User user);

    /**
     * Updates a User entity in the database.
     *
     * @param user The User entity to be updated.
     * @throws PersistenceNonExistentUserException when the user does not exist in the database.
     */
    void updateUser(User user) throws PersistenceNonExistentUserException;

    /**
     * Deletes a User entity from the database based on its username.
     *
     * @param user The User entity to be deleted.
     * @throws PersistenceNonExistentUserException when the user does not exist in the database.
     */
    void deleteUser(User user) throws PersistenceNonExistentUserException;

    /**
     * Gets a User entity from the database based on its username.
     *
     * @param user The User entity to be fetched.
     * @return The fetched User entity, or throws PersistenceNonExistentUserException if not found.
     * @throws PersistenceNonExistentUserException If the user with the given username is not found.
     */
    User getUser(User user) throws PersistenceNonExistentUserException;

    /**
     * Gets a list of User entities from the database.
     *
     * @return The list of User entities.
     */
    List<User> getUsers();

    /**
     * Gets all users for a specific course.
     * @param course The course.
     * @return The users.
     * @throws PersistenceNonExistentCourseException when the course does not exist.
     */
    List<User> getUsersForCourse(Course course) throws PersistenceNonExistentCourseException;

    /**
     * Updates a users SystemRole after verification.
     *
     * @param user The user to verify.
     */
    void verifyUser(User user);

    /**
     * Checks if a username already exists in the database.
     *
     * @param user The user for whom to check.
     * @return true, in case the username exists, false otherwise.
     */
    boolean userNameExists(User user);

    /**
     * Checks if an email already exists in the database.
     *
     * @param user The user for whom to check.
     * @return true, in case the email exists, false otherwise.
     */
    boolean emailExists(User user);

    /**
     * Sets the users new password by matching the given verification token.
     *
     * @param user The user to reset the password for.
     * @return Whether the reset was successfully or not.
     */
    boolean resetPassword(User user);

    /**
     * Updates a users role in a course.
     *
     * @param user The user.
     * @param course The course.
     * @param courseRole The role.
     */
    void updateCourseRole(User user, Course course, CourseRole courseRole);

    /**
     * Deletes the users which have not been verified for 3 days.
     */
    void deleteUnverifiedUsers();

    /**
     * Resets the Number of Attempts to enter a password for every user every hour.
     */
    void resetPasswordAttempts();
}
