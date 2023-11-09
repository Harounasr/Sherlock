package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class SubmissionFileRepositoryPsql implements SubmissionFileRepository {

    private final Logger logger = LoggerCreator.get(SubmissionFileRepositoryPsql.class);

    @Override
    public void insertSubmissionFile(long submissionId, File file) {

    }

    @Override
    public void updateSubmissionFile(long id, File file) {

    }

    @Override
    public void deleteSubmissionFile(long id) {

    }

    @Override
    public File fetchSubmissionFile(long id) {
        return null;
    }

    @Override
    public List<File> fetchTestateComments(Predicate<File> predicate) {
        return null;
    }
}
