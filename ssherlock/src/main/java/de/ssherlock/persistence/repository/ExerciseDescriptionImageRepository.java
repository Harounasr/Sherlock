package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.exception.PersistenceNonExistentImageException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 * Interface for interacting with a repository of ExerciseDescriptionImage entities in the database.
 */
public interface ExerciseDescriptionImageRepository {

    /**
     * Inserts an ExerciseDescriptionImage into the database.
     *
     * @param image The image.
     */
    void insertExerciseDescriptionImage(ExerciseDescriptionImage image);

    /**
     * Gets an ExerciseDescriptionImage from the database by its UUID.
     *
     * @param uuid the UUID.
     * @return The image.
     *
     * @throws PersistenceNonExistentImageException when the image does not exist in the database.
     */
    ExerciseDescriptionImage getExerciseDescriptionImage(String uuid) throws PersistenceNonExistentImageException;

}
