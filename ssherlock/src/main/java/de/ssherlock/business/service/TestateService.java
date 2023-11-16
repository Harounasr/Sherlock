package de.ssherlock.business.service;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * The TestateService class provides functionality for managing testates and related operations.
 */
@Named
@Dependent
public class TestateService implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to TestateService.
     */
    private final SerializableLogger logger;

    /**
     * Constructs a TestateService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to TestateService.
     */
    @Inject
    public TestateService(SerializableLogger logger) {
        this.logger = logger;
    }

    /**
     * Retrieves a list of testates associated with the specified exercise.
     *
     * @param exercise The exercise for which to retrieve testates.
     * @return A list of testates associated with the exercise.
     */
    public List<Testate> getTestates(Exercise exercise) {
        return null;
    }

    /**
     * Retrieves a list of assigned testates associated with the specified exercise and user.
     *
     * @param exercise The exercise for which to retrieve assigned testates.
     * @param user     The user for whom to retrieve assigned testates.
     * @return A list of assigned testates associated with the exercise and user.
     */
    public List<Testate> getAssignedTestates(Exercise exercise, User user) {
        return null;
    }

    /**
     * Retrieves a testate associated with the specified exercise and user.
     *
     * @param exercise The exercise for which to retrieve the testate.
     * @param user     The user for whom to retrieve the testate.
     * @return The testate associated with the exercise and user.
     */
    public Testate getTestate(Exercise exercise, User user) {
        return null;
    }

    /**
     * Updates the information of an existing testate.
     *
     * @param testate The testate to be updated.
     */
    public void updateTestate(Testate testate) {

    }
}

