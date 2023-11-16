package de.ssherlock.global.transport;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Course DTO.
 *
 * @param name The name of the course.
 * @param users Map of users and their role in the course.
 * @param exercises List of all exercises.
 */
public record Course(
        String name,
        Map<User, CourseRole> users,
        List<Exercise> exercises
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(users, course.users) && Objects.equals(exercises, course.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, users, exercises);
    }

    /**
     * Builder class for constructing Course instances.
     */
    public static class Builder {
        private String name;
        private Map<User, CourseRole> users;
        private List<Exercise> exercises;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Method body intentionally left empty
        }

        /**
         * Copies attributes from an existing Course instance.
         *
         * @param course The Course instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Course course) {
            this.name = course.name();
            this.users = course.users();
            this.exercises = course.exercises();
            return this;
        }

        /**
         * Sets the name for the Course.
         *
         * @param name The name to set.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the users for the Course.
         *
         * @param users The users to set.
         * @return The Builder instance.
         */
        public Builder users(Map<User, CourseRole> users) {
            this.users = users;
            return this;
        }

        /**
         * Sets the exercises for the Course.
         *
         * @param exercises The exercises to set.
         * @return The Builder instance.
         */
        public Builder exercises(List<Exercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        /**
         * Builds a Course instance using the provided attributes.
         *
         * @return The constructed Course instance.
         */
        public Course build() {
            return new Course(name, users, exercises);
        }
    }
}
