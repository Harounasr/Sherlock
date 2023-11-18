package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a User DTO.
 */
@Named
@Dependent
public class User implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

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
    private Map<String, CourseRole> courseRoles;

    /**
     * Instantiates a new User.
     */
    public User() {

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
    public Map<String, CourseRole> getCourseRoles() {
        return courseRoles;
    }

    /**
     * Sets course roles.
     *
     * @param courseRoles the course roles
     */
    public void setCourseRoles(Map<String, CourseRole> courseRoles) {
        this.courseRoles = courseRoles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && systemRole == user.systemRole && Objects.equals(password, user.password) && Objects.equals(facultyName, user.facultyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, email, firstName, lastName, systemRole, password, facultyName);
    }
}
