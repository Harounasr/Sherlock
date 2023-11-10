package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Exercise;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class ExerciseRepositoryPsql extends RepositoryPsql implements ExerciseRepository {

    private final Logger logger = LoggerCreator.get(CheckerResultRepositoryPsql.class);
    public ExerciseRepositoryPsql(Connection connection) {
        super(connection);
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
