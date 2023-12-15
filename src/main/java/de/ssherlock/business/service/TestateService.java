package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.business.exception.BusinessNonExistentTestateException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Pagination;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentSubmissionException;
import de.ssherlock.persistence.exception.PersistenceNonExistentTestateException;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.SubmissionRepository;
import de.ssherlock.persistence.repository.TestateRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The TestateService class provides functionality for managing testates and related operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class TestateService implements Serializable {

    /**
     * Serial Version UID.
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
     * @param logger         The logger to be used for logging messages related to TestateService.
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
     * @param exercise The exercise for which to retrieve assigned testates.
     * @param user     The user for whom to retrieve assigned testates.
     * @return A list of assigned testates associated with the exercise and user.
     */
    public List<Testate> getAssignedTestates(Exercise exercise, User user) {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository =
                RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        List<Testate> testate = testateRepository.getTestates(exercise, user);
        connectionPool.releaseConnection(connection);
        return testate;
    }

    /**
     * Gets all assigned testates depending on the pagination.
     *
     * @param pagination The pagination
     * @param exercise The exercise
     * @param user The user.
     * @return The list of testates
     */
    public List<Testate> getAssignedTestates(Pagination pagination, Exercise exercise, User user) {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository = RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        List<Testate> testates = testateRepository.getTestates(exercise, user);
        connectionPool.releaseConnection(connection);
        return sortAndFilter(testates, pagination);
    }

    /**
     * Retrieves a list of all testates associated with the specified exercise.
     *
     * @param exercise The exercise for which to retrieve the testates.
     * @return A list of assigned testates associated with the exercise and user.
     */
    public List<Testate> getAllTestates(Exercise exercise) {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository =
                RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        List<Testate> testates = testateRepository.getTestates(exercise);
        connectionPool.releaseConnection(connection);
        return testates;
    }

    /**
     * Gets all testates depending on the pagination.
     *
     * @param pagination The pagination
     * @param exercise The exercise
     * @return The list of testates
     */
    public List<Testate> getAllTestates(Pagination pagination, Exercise exercise) {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository = RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        List<Testate> testates = testateRepository.getTestates(exercise);
        connectionPool.releaseConnection(connection);
        return sortAndFilter(testates, pagination);
    }

    /**
     * Retrieves a testate associated with the specified exercise and user.
     *
     * @param exercise The exercise for which to retrieve the testate.
     * @param user     The user for whom to retrieve the testate.
     * @return The testate associated with the exercise and user.
     * @throws BusinessNonExistentTestateException when the testate does not exist in the database.
     */
    public Testate getTestate(Exercise exercise, User user)
            throws BusinessNonExistentTestateException {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository = RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        Testate testate = new Testate();
        try {
            testate = testateRepository.getTestate(exercise, user);
        } catch (PersistenceNonExistentTestateException e) {
            throw new BusinessNonExistentTestateException();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        connection = connectionPool.getConnection();
        SubmissionRepository submissionRepository = RepositoryFactory.getSubmissionRepository(RepositoryType.POSTGRESQL, connection);
        try {
            testate.setSubmission(submissionRepository.getSubmissions(exercise).getFirst());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return testate;
    }

    /**
     * Updates the information of an existing testate.
     *
     * @param testate The testate to be updated.
     */
    public void addTestate(Testate testate) {
        Connection connection = connectionPool.getConnection();
        TestateRepository testateRepository = RepositoryFactory.getEvaluationRepository(RepositoryType.POSTGRESQL, connection);
        testateRepository.insertTestate(testate);
    }

    /**
     * Sorts and filters a list of testates based on the pagination.
     *
     * @param testates The testates.
     * @param pagination The pagination.
     * @return The sorted and filtered list.
     */
    private List<Testate> sortAndFilter(List<Testate> testates, Pagination pagination) {
        Stream<Testate> testateStream = testates.stream();

        if (!pagination.getSearchString().isEmpty()) {
            testateStream = testateStream.filter(testate -> testate.getEvaluatorId() == (Integer.parseInt(pagination.getSearchString()))
                                                            || testate.getStudent().contains(pagination.getSearchString()));
        }

        String sortBy = pagination.getSortBy();
        if (!sortBy.isEmpty()) {
            Comparator<Testate> comparator = switch (sortBy) {
                case "student" -> Comparator.comparing(Testate::getStudent);
                case "tutor" -> Comparator.comparing(Testate::getEvaluatorId);
                default -> (testate1, testate2) -> 0;
            };
            testateStream = pagination.isSortAscending() ? testateStream.sorted(comparator) : testateStream.sorted(comparator.reversed());
        }

        return testateStream.collect(Collectors.toList());
    }
}
