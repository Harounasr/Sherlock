package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.SubmissionFile;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class SubmissionFileRepositoryPsql implements SubmissionFileRepository {

    private final Logger logger = LoggerCreator.get(SubmissionFileRepositoryPsql.class);

    public SubmissionFileRepositoryPsql(Connection connection) {

    }

    @Override
    public void insertSubmissionFile(long submissionId, SubmissionFile file) {

    }

    @Override
    public void updateSubmissionFile(long id, SubmissionFile file) {

    }

    @Override
    public void deleteSubmissionFile(long id) {

    }

    @Override
    public SubmissionFile fetchSubmissionFile(long id) {
        return null;
    }

    @Override
    public List<SubmissionFile> fetchTestateComments(Predicate<SubmissionFile> predicate) {
        return null;
    }
}
