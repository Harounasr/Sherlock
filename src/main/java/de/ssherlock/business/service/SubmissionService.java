package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessDBAccessException;
import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.exception.BusinessNonExistentSubmissionException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Pagination;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceDBAccessException;
import de.ssherlock.persistence.exception.PersistenceNonExistentCourseException;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import de.ssherlock.persistence.repository.SubmissionRepository;
import de.ssherlock.persistence.repository.UserRepository;
import de.ssherlock.persistence.transaction.Transaction;
import de.ssherlock.persistence.transaction.TransactionPsql;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The SubmissionService class provides functionality for managing submissions and related
 * operations.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class SubmissionService implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to SubmissionService.
     */
    private final SerializableLogger logger;

    /**
     * The connection pool instance.
     */
    private final ConnectionPool connectionPool;

    /**
     * Constructs a SubmissionService with the specified logger.
     *
     * @param logger         The logger to be used for logging messages related to SubmissionService.
     * @param connectionPool The connection pool instance.
     */
    @Inject
    public SubmissionService(SerializableLogger logger, ConnectionPool connectionPool) {
        this.logger = logger;
        this.connectionPool = connectionPool;
    }

    /**
     * Adds a new submission.
     *
     * @param submission The submission to be added.
     */
    public void addSubmission(Submission submission) {
        Connection connection = connectionPool.getConnection();
        SubmissionRepository submissionRepository = RepositoryFactory.getSubmissionRepository(RepositoryType.POSTGRESQL, connection);
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        submission.setTimestamp(timestamp);
        submissionRepository.insertSubmission(submission);
    }

    /**
     * Retrieves a list of submissions associated with the specified exercise.
     *
     * @param pagination The pagination
     * @param exercise   The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the exercise.
     * @throws BusinessDBAccessException When the access is denied.
     * @throws BusinessNonExistentCourseException When the course does not exist.
     */
    public List<Submission> getSubmissions(Pagination pagination, Exercise exercise)
            throws BusinessDBAccessException, BusinessNonExistentCourseException {
        Transaction transaction;
        try {
            transaction = new TransactionPsql(connectionPool.getConnection());
        } catch (PersistenceDBAccessException e) {
            throw new BusinessDBAccessException();
        }
        UserRepository userRepository = RepositoryFactory.getUserRepository(RepositoryType.POSTGRESQL, transaction.getConnection());
        List<User> users;
        try {
            Course course = new Course();
            course.setId(exercise.getCourseId());
            users = userRepository.getUsersForCourse(course);
        } catch (PersistenceNonExistentCourseException e) {
            transaction.abort();
            throw new BusinessNonExistentCourseException();
        }
        SubmissionRepository submissionRepository = RepositoryFactory.getSubmissionRepository(RepositoryType.POSTGRESQL, transaction.getConnection());
        List<Submission> submissions = submissionRepository.getSubmissions(exercise, users);
        return sortAndFilterSubmissions(submissions, pagination);
    }

    /**
     * Retrieves a list of submissions associated with the specified user and exercise.
     *
     * @param pagination The pagination.
     * @param user       The user for whom to retrieve submissions.
     * @param exercise   The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the user and exercise.
     */
    public List<Submission> getSubmissionsForStudent(Pagination pagination, User user, Exercise exercise) {
        Connection connection = connectionPool.getConnection();
        SubmissionRepository submissionRepository = RepositoryFactory.getSubmissionRepository(RepositoryType.POSTGRESQL, connection);
        List<Submission> submissions = submissionRepository.getSubmissionsForStudent(exercise, user);
        connectionPool.releaseConnection(connection);
        return sortAndFilterSubmissions(submissions, pagination);
    }

    /**
     * Retrieves a list of submissions associated with the specified user and exercise.
     *
     * @param pagination The pagination.
     * @param user       The user for whom to retrieve submissions.
     * @param exercise   The exercise for which to retrieve submissions.
     * @return A list of submissions associated with the user and exercise.
     */
    public List<Submission> getSubmissionsForTutor(Pagination pagination, User user, Exercise exercise) {
        Connection connection = connectionPool.getConnection();
        SubmissionRepository submissionRepository = RepositoryFactory.getSubmissionRepository(RepositoryType.POSTGRESQL, connection);
        List<Submission> submissions = submissionRepository.getSubmissionsForTutor(exercise, user);
        return sortAndFilterSubmissions(submissions, pagination);
    }

    /**
     * Retrieves the newest submissions associated with the specified exercise.
     *
     * @param pagination The pagination.
     * @param exercise   The exercise for which to retrieve the newest submissions.
     * @return A list of the newest submissions associated with the exercise.
     */
    public List<Submission> getNewestSubmissions(Pagination pagination, Exercise exercise) {
        return null;
    }

    /**
     * Gets the submission associated with the provided id.
     *
     * @param submission The submission to retrieve.
     * @return The retrieved submission.
     * @throws BusinessNonExistentSubmissionException when the submission does not exist in the
     *                                                database.
     */
    public Submission getSubmission(Submission submission)
            throws BusinessNonExistentSubmissionException {
        return null;
    }

    /**
     * Sorts and filters a list of submission based on a pagination.
     *
     * @param submissions The list to sort and filter
     * @param pagination  The pagination
     * @return The sorted and filtered list.
     */
    private List<Submission> sortAndFilterSubmissions(List<Submission> submissions, Pagination pagination) {
        Stream<Submission> submissionStream = submissions.stream();

        if (!pagination.getSearchString().isEmpty()) {
            submissionStream = submissionStream.filter(submission -> submission.getUser().contains(pagination.getSearchString()));
        }

        String sortBy = pagination.getSortBy();
        if (!sortBy.isEmpty()) {
            Comparator<Submission> comparator = switch (sortBy) {
                case "username" -> Comparator.comparing(Submission::getUser);
                case "tutor" -> Comparator.comparing(Submission::getTutor);
                case "timestamp" -> Comparator.comparing(Submission::getTimestamp);
                default -> (submission1, submission2) -> 0;
            };
            submissionStream = pagination.isSortAscending() ? submissionStream.sorted(comparator) : submissionStream.sorted(comparator.reversed());
        }

        return submissionStream.collect(Collectors.toList());
    }
}
