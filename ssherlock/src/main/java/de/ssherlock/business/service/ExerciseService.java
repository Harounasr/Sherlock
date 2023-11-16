package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * The ExerciseService class provides functionality for managing exercises and related operations.
 */
@Named
@Dependent
public class ExerciseService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to ExerciseService.
     */
    private final SerializableLogger logger;

    /**
     * Constructs an ExerciseService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to ExerciseService.
     */
    @Inject
    public ExerciseService(SerializableLogger logger) {
        this.logger = logger;
    }

    /**
     * Retrieves a list of exercises associated with the specified course.
     *
     * @param course The course for which to retrieve exercises.
     * @return A list of exercises associated with the course.
     */
    public List<Exercise> getExercises(Course course) {
        return null;
    }

    /**
     * Updates the information of an existing exercise.
     *
     * @param exercise The exercise to be updated.
     */
    public void updateExercise(Exercise exercise) {

    }

    /**
     * Adds a new exercise.
     *
     * @param exercise The exercise to be added.
     */
    public void addExercise(Exercise exercise) {

    }

    /**
     * Removes an existing exercise.
     *
     * @param exercise The exercise to be removed.
     */
    public void removeExercise(Exercise exercise) {

    }

    /**
     * Retrieves an exercise based on its unique identifier.
     *
     * @param id The unique identifier of the exercise.
     * @return The exercise with the specified identifier.
     */
    public Exercise getExercise(long id) {
        return null;
    }

    /**
     * Adds an exercise description image.
     *
     * @param exerciseDescriptionImage The image to be added to the exercise description.
     * @return A string indicating the result of the operation.
     */
    public String addExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage) {
        return null;
    }
}




