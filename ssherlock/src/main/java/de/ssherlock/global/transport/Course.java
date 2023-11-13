package de.ssherlock.global.transport;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static class Builder {
        private String name;
        private Map<User, CourseRole> users;
        private List<Exercise> exercises;

        public Builder() {
        }

        public Builder copyFrom(Course course) {
            this.name = course.name();
            this.users = course.users();
            this.exercises = course.exercises();
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder users(Map<User, CourseRole> users) {
            this.users = users;
            return this;
        }

        public Builder exercises(List<Exercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        public Course build() {
            return new Course(name, users, exercises);
        }
    }
}
