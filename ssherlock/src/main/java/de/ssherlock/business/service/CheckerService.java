package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.Exercise;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * The CheckerService class provides functionality for managing checkers and related operations.
 */
@Named
@Dependent
public class CheckerService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to CheckerService.
     */
    private final SerializableLogger logger;

    /**
     * Constructs a CheckerService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to CheckerService.
     */
    @Inject
    public CheckerService(SerializableLogger logger) {
        this.logger = logger;
    }

    /**
     * Adds a new checker.
     *
     * @param checker The checker to be added.
     */
    public void addChecker(Checker checker) {

    }

    /**
     * Removes an existing checker.
     *
     * @param checker The checker to be removed.
     */
    public void removeChecker(Checker checker) {

    }

    /**
     * Retrieves a list of checkers associated with the specified exercise.
     *
     * @param exercise The exercise for which to retrieve checkers.
     * @return A list of checkers associated with the exercise.
     */
    public List<Checker> getCheckersForExercise(Exercise exercise) {
        return null;
    }

    /**
     * Updates the information of an existing checker.
     *
     * @param checker The checker to be updated.
     */
    public void updateChecker(Checker checker) {

    }

    /**
     * Retrieves a checker based on its unique identifier.
     *
     * @param id The unique identifier of the checker.
     * @return The checker with the specified identifier.
     */
    public Checker getCheckerByID(long id) {
        return null;
    }
}



