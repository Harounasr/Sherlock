package de.ssherlock.global.transport;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && systemRole == user.systemRole && Objects.equals(password, user.password) && Objects.equals(facultyName, user.facultyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, firstName, lastName, systemRole, password, facultyName);
    }

    public static class Builder {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private SystemRole systemRole;
        private Password password;
        private String facultyName;
        private Map<String, CourseRole> courseRoles;

        public Builder() {
        }

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder systemRole(SystemRole systemRole) {
            this.systemRole = systemRole;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder facultyName(String facultyName) {
            this.facultyName = facultyName;
            return this;
        }

        public Builder courseRoles(Map<String, CourseRole> courseRoles) {
            this.courseRoles = courseRoles;
            return this;
        }

        public User build() {
            return new User(username, email, firstName, lastName, systemRole, password, facultyName, courseRoles);
        }
    }
}
