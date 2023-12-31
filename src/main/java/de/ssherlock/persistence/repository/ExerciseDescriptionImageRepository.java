package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.exception.PersistenceNonExistentImageException;

/**
 * Interface for interacting with a repository of ExerciseDescriptionImage entities in the database.
 *
 * @author Leon Höfling
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
   * @param exerciseDescriptionImage The image to fetch.
   * @return The image.
   * @throws PersistenceNonExistentImageException when the image does not exist in the database.
   */
  ExerciseDescriptionImage getExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage)
      throws PersistenceNonExistentImageException;

    /**
     * Deletes all the unused exercise Images from the database.
     */
  void cleanUnusedImages();
}
