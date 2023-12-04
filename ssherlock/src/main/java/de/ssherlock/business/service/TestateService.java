package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentTestateException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.persistence.connection.ConnectionPool;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The TestateService class provides functionality for managing testates and related operations.
 *
 * @author Leon Höfling
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
     * The connection pool instance.
     */
    private final ConnectionPool connectionPool;

    /**
     * Constructs a TestateService with the specified logger.
     *
     * @param logger             The logger to be used for logging messages related to TestateService.
     * @param connectionPool The connection pool instance.
     */
    @Inject
    public TestateService(SerializableLogger logger, ConnectionPool connectionPool) {
        this.logger = logger;
        this.connectionPool = connectionPool;
    }

    /**
     * Retrieves a list of assigned testates associated with the specified exercise and user.
     *
     * @param exerciseId The exercise for which to retrieve assigned testates.
     * @param username     The user for whom to retrieve assigned testates.
     * @return A list of assigned testates associated with the exercise and user.
     */
    public List<Testate> getAssignedTestates(long exerciseId, String username) {
        return null;
    }

    /**
     * Retrieves a list of all testates associated with the specified exercise.
     *
     * @param exerciseId The exercise for which to retrieve the testates.
     * @return A list of assigned testates associated with the exercise and user.
     */
    public List<Testate> getAllTestates(long exerciseId) {
        return null;
    }

    /**
     * Retrieves a testate associated with the specified exercise and user.
     *
     * @param exerciseId The exercise for which to retrieve the testate.
     * @param username     The user for whom to retrieve the testate.
     * @return The testate associated with the exercise and user.
     *
     * @throws BusinessNonExistentTestateException when the testate does not exist in the database.
     */
    public Testate getTestate(long exerciseId, String username) throws BusinessNonExistentTestateException {
        return null;
    }

    /**
     * Updates the information of an existing testate.
     *
     * @param testate The testate to be updated.
     */
    public void addTestate(Testate testate) {

    }
}

