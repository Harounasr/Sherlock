package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a User DTO.
 *
 * @author Victor Vollmann
 */
public class User implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The user's id.
     */
    private long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email-address of the user.
     */
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The SystemRole of the user.
     */
    private SystemRole systemRole;

    /**
     * The password of the user.
     */
    private Password password;

    /**
     * The name of the faculty of the user.
     */
    private String facultyName;

    /**
     * The roles the user has in all courses he/she is in.
     */
    private Map<Long, CourseRole> courseRoles;

    /**
     * The token to verify the user's registration.
     */
    private String verificationToken;

    /**
     * The number of failed login attempts between the last two full hours.
     */
    private int failedLoginAttempts;

    /**
     * The timestamp until the verificationToken will be valid.
     */
    private Timestamp expiryDate;

    /**
     * Instantiates a new User.
     */
    public User() {}

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets system role.
     *
     * @return the system role
     */
    public SystemRole getSystemRole() {
        return systemRole;
    }

    /**
     * Sets system role.
     *
     * @param systemRole the system role
     */
    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public Password getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(Password password) {
        this.password = password;
    }

    /**
     * Gets faculty name.
     *
     * @return the faculty name
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * Sets faculty name.
     *
     * @param facultyName the faculty name
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * Gets course roles.
     *
     * @return the course roles
     */
    public Map<Long, CourseRole> getCourseRoles() {
        return courseRoles;
    }

    /**
     * Sets course roles.
     *
     * @param courseRoles the course roles
     */
    public void setCourseRoles(Map<Long, CourseRole> courseRoles) {
        this.courseRoles = courseRoles;
    }

    /**
     * Gets verification token.
     *
     * @return the verification token
     */
    public String getVerificationToken() {
        return verificationToken;
    }

    /**
     * Sets verification token.
     *
     * @param verificationToken the verification token
     */
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    /**
     * Gets failed login attempts.
     *
     * @return the failed login attempts
     */
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * Sets failed login attempts.
     *
     * @param failedLoginAttempts the failed login attempts
     */
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    /**
     * Gets expiry date.
     *
     * @return the expiry date.
     */
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets expiry date.
     *
     * @param expiryDate the expiry date.
     */
    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}
