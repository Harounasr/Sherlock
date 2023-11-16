package de.ssherlock.global.transport;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a User DTO
 *
 * @param username    The username of the user.
 * @param email       The email-address of the user.
 * @param firstName   The first name of the user.
 * @param lastName    The last name of the user.
 * @param systemRole  The SystemRole of the user.
 * @param password    The password of the user.
 * @param facultyName The name of the faculty of the user.
 * @param courseRoles The roles the user has in all courses he/she is in.
 */
public record User(
        String username,
        String email,
        String firstName,
        String lastName,
        SystemRole systemRole,
        Password password,
        String facultyName,
        Map<String, CourseRole> courseRoles
) {
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

    /**
     * Builder class for constructing User instances.
     */
    public static class Builder {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private SystemRole systemRole;
        private Password password;
        private String facultyName;
        private Map<String, CourseRole> courseRoles;

        /**
         * Initializes the builder.
         */
        public Builder() {
        }

        /**
         * Copies attributes from an existing User instance.
         *
         * @param user The User instance to copy attributes from.
         * @return This builder with updated attributes.
         */
        public Builder copyFrom(User user) {
            this.username = user.username();
            this.email = user.email();
            this.firstName = user.firstName();
            this.lastName = user.lastName();
            this.systemRole = user.systemRole();
            this.password = user.password();
            this.facultyName = user.facultyName();
            this.courseRoles = user.courseRoles();
            return this;
        }

        /**
         * Sets the username.
         *
         * @param username The username of the user.
         * @return This builder with the updated username.
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email.
         *
         * @param email The email address of the user.
         * @return This builder with the updated email.
         */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the first name.
         *
         * @param firstName The first name of the user.
         * @return This builder with the updated first name.
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets the last name.
         *
         * @param lastName The last name of the user.
         * @return This builder with the updated last name.
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets the system role.
         *
         * @param systemRole The SystemRole of the user.
         * @return This builder with the updated system role.
         */
        public Builder systemRole(SystemRole systemRole) {
            this.systemRole = systemRole;
            return this;
        }

        /**
         * Sets the password.
         *
         * @param password The password of the user.
         * @return This builder with the updated password.
         */
        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the faculty name.
         *
         * @param facultyName The name of the faculty of the user.
         * @return This builder with the updated faculty name.
         */
        public Builder facultyName(String facultyName) {
            this.facultyName = facultyName;
            return this;
        }

        /**
         * Sets the course roles.
         *
         * @param courseRoles The roles the user has in all courses he/she is in.
         * @return This builder with the updated course roles.
         */
        public Builder courseRoles(Map<String, CourseRole> courseRoles) {
            this.courseRoles = courseRoles;
            return this;
        }

        /**
         * Builds a new User instance with the provided attributes.
         *
         * @return A new User instance.
         */
        public User build() {
            return new User(username, email, firstName, lastName, systemRole, password, facultyName, courseRoles);
        }
    }
}
