package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;

import java.util.List;
import java.util.function.Predicate;

public interface ExerciseRepository {
    void insertExercise(Exercise exercise);
    void updateExercise(Exercise exercise);
    void deleteExercise(String exerciseName);
    Exercise fetchExercise(String exerciseName);
    List<Exercise> fetchExercises(Predicate<Exercise> predicate);
}
