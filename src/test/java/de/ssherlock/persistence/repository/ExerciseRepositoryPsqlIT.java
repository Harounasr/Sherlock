package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Test class for {@link ExerciseRepositoryPsql}.
 */
public class ExerciseRepositoryPsqlIT {
    private Exercise exercise;

    /**
     * Initializes the exercise before each test.
     */
    @BeforeEach
    public void init () throws SQLException {
        exercise = new Exercise();
        exercise.setId(10);
        exercise.setName("newExercise");
        LocalDateTime currentDateTime = LocalDateTime.now();
        exercise.setPublishDate(Timestamp.valueOf(currentDateTime));
        LocalDateTime recommendedDeadlineDateTime = currentDateTime.plusDays(7);
        exercise.setRecommendedDeadline(Timestamp.valueOf(recommendedDeadlineDateTime));
        LocalDateTime obligatoryDeadlineDateTime = recommendedDeadlineDateTime.plusDays(7);
        exercise.setObligatoryDeadline(Timestamp.valueOf(obligatoryDeadlineDateTime));
        exercise.setDescription("<h1>Exercise 10 Description</h2>");
        exercise.setCourseId(6);
    }


    /**
     * Checks if two exercises are equal.
     */
    boolean areExercisesEqual(Exercise exercise1, Exercise exercise2) {
        return exercise1.getName().equals(exercise2.getName())
               && exercise1.getDescription().equals(exercise2.getDescription())
               && exercise1.getCourseId() == (exercise2.getCourseId())
               && exercise1.getPublishDate().equals(exercise2.getPublishDate())
               && exercise1.getRecommendedDeadline().equals(exercise2.getRecommendedDeadline());

    }

    /**
     * Tests the updateExercise and getExercise methods.
     */
    @Test
    void testUpdateExerciseAndGetExercise() throws SQLException {
        Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
        ExerciseRepository exerciseRepository =
                RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);

        Exercise updatedExercise = new Exercise();
        try {
            exerciseRepository.updateExercise(exercise);
            updatedExercise = exerciseRepository.getExercise(exercise);
        } catch (PersistenceNonExistentExerciseException e) {
            throw new RuntimeException("The requested exercise does not exist.", e);
        }
        assertTrue(areExercisesEqual(exercise, updatedExercise));
    }
}
