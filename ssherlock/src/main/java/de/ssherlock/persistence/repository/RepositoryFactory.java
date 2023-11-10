package de.ssherlock.persistence.repository;

import java.sql.Connection;
import java.util.logging.Logger;

public class RepositoryFactory {

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

    public static CheckerResultRepository getCheckerResultRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new CheckerResultRepositoryPsql(connection);
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

    public static SubmissionFileRepository getSubmissionFileRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new SubmissionFileRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

    public static TestateCommentRepository getTestateCommentRepository(RepositoryType type, Connection connection) {
        switch (type) {
            case POSTGRESQL -> {
                return new TestateCommentRepositoryPsql(connection);
            }
            default -> {
                return null;
            }
        }
    }

}
