package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;

import java.sql.Connection;
import java.util.logging.Logger;

public class RepositoryFactory {

    private static final SerializableLogger logger = LoggerCreator.getSerial(RepositoryFactory.class);

    public static CheckerRepository getCheckerRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new CheckerRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static CourseRepository getCourseRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new CourseRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static TestateRepository getEvaluationRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new TestateRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static ExerciseRepository getExerciseRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new ExerciseRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static SubmissionRepository getSubmissionRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new SubmissionRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static SystemSettingsRepository getSystemSettingsRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new SystemSettingsRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static UserRepository getUserRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new UserRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static ExerciseDescriptionImageRepository getExerciseDescriptionImageRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new ExerciseDescriptionImageRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static NotVerifiedUserRepository getNotVerifiedUserRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new NotVerifiedUserRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

}
