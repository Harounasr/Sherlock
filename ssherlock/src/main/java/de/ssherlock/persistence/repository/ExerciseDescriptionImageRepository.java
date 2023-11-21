package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

public interface ExerciseDescriptionImageRepository {

    String insertExerciseDescriptionImage(ExerciseDescriptionImage image);

    void updateExerciseDescriptionImage(ExerciseDescriptionImage image);

    void deleteExerciseDescriptionImage(String uuid);

    ExerciseDescriptionImage getExerciseDescriptionImage(String uuid);

}
