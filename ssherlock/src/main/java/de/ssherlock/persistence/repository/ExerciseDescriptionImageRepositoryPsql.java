package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.ExerciseDescriptionImage;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class ExerciseDescriptionImageRepositoryPsql implements ExerciseDescriptionImageRepository{

    private final Logger logger = LoggerCreator.get(ExerciseDescriptionImageRepositoryPsql.class);
    @Override
    public void insertExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage) {

    }

    @Override
    public void updateExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage) {

    }

    @Override
    public void deleteExerciseDescriptionImage(long id) {

    }

    @Override
    public ExerciseDescriptionImage fetchExerciseDescriptionImage(long id) {
        return null;
    }

    @Override
    public List<ExerciseDescriptionImage> fetchExerciseDescriptionImage(Predicate<ExerciseDescriptionImage> predicate) {
        return null;
    }
}
