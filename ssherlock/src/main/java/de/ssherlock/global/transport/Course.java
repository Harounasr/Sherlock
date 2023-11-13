package de.ssherlock.global.transport;

import java.util.List;
import java.util.Map;

public record Course(
        String name,
        Map<User, CourseRole> users,
        List<Exercise> exercises
) {
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
