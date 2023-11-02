package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Exercise;

import java.util.List;
import java.util.function.Predicate;

public class ExerciseRepositoryPsql extends RepositoryPsql implements ExerciseRepository {

    public ExerciseRepositoryPsql() {
        super();
    }

    @Override
    public void insertExercise(Exercise exercise) {

    }

    @Override
    public void updateExercise(Exercise exercise) {

    }

    @Override
    public void deleteExercise(String exerciseName) {

    }

    @Override
    public Exercise fetchExercise(String exerciseName) {
        return null;
    }

    @Override
    public List<Exercise> fetchExercises(Predicate<Exercise> predicate) {
        return null;
    }
}
