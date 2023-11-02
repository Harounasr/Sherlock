package de.ssherlock.persistence.repository;

import java.util.logging.Logger;

public class RepositoryFactory {

    private Logger logger;

    public static CheckerRepository getCheckerRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new CheckerRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static CourseRepository getCourseRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new CourseRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static EvaluationRepository getEvaluationRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new EvaluationRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static ExerciseRepository getExerciseRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new ExerciseRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static SubmissionRepository getSubmissionRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new SubmissionRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static SystemSettingsRepository getSystemSettingsRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new SystemSettingsRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

    public static UserRepository getUserRepository(RepositoryType type) {
        switch (type) {
            case POSTGRESQL -> {
                return new UserRepositoryPsql();
            }
            default -> {
                return null;
            }
        }
    }

}
