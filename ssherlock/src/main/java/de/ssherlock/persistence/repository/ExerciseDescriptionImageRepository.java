package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.ExerciseDescriptionImage;

import java.util.List;
import java.util.function.Predicate;

public interface ExerciseDescriptionImageRepository {
    void insertExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage);
    void updateExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage);
    void deleteExerciseDescriptionImage(long id);
    ExerciseDescriptionImage fetchExerciseDescriptionImage(long id);
    List<ExerciseDescriptionImage> fetchExerciseDescriptionImage(Predicate<ExerciseDescriptionImage> predicate);
}
