package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import java.sql.Connection;

/**
 * Factory class to create various repositories based on the provided RepositoryType.
 *
 * @author Victor Vollmann
 */
public final class RepositoryFactory {

  /** Logger instance for this class. */
  private static final SerializableLogger LOGGER = LoggerCreator.get(RepositoryFactory.class);

  /** Default constructor. */
  private RepositoryFactory() {}

  /**
   * Returns a CheckerRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A CheckerRepository based on the RepositoryType.
   */
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

  /**
   * Returns a CourseRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A CourseRepository based on the RepositoryType.
   */
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

  /**
   * Returns a TestateRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A TestateRepository based on the RepositoryType.
   */
  public static TestateRepository getEvaluationRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new TestateRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a ExerciseRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A ExerciseRepository based on the RepositoryType.
   */
  public static ExerciseRepository getExerciseRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new ExerciseRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a SubmissionRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A SubmissionRepository based on the RepositoryType.
   */
  public static SubmissionRepository getSubmissionRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new SubmissionRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a SystemSettingsRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A SystemSettingsRepository based on the RepositoryType.
   */
  public static SystemSettingsRepository getSystemSettingsRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new SystemSettingsRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a UserRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A UserRepository based on the RepositoryType.
   */
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

  /**
   * Returns a ExerciseDescriptionImageRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A ExerciseDescriptionImageRepository based on the RepositoryType.
   */
  public static ExerciseDescriptionImageRepository getExerciseDescriptionImageRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new ExerciseDescriptionImageRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a NotVerifiedUserRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A NotVerifiedUserRepository based on the RepositoryType.
   */
  public static NotVerifiedUserRepository getNotVerifiedUserRepository(
      RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new NotVerifiedUserRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }

  /**
   * Returns a FacultyRepository based on the RepositoryType and connection.
   *
   * @param type The RepositoryType to determine the repository type.
   * @param connection The database connection.
   * @return A FacultyRepository based on the RepositoryType.
   */
  public static FacultyRepository getFacultyRepository(RepositoryType type, Connection connection) {
    switch (type) {
      case POSTGRESQL -> {
        return new FacultyRepositoryPsql(connection);
      }
      default -> {
        return null;
      }
    }
  }
}
