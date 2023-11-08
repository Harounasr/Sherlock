package de.ssherlock.persistence.repository;

import java.sql.Connection;
import java.util.logging.Logger;

public class getRepositoryFactory {

    private Logger logger;

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

    public static EvaluationRepository getEvaluationRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new EvaluationRepositoryPsql(connection);
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

}
