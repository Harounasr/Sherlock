package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Submission;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class SubmissionRepositoryPsql extends RepositoryPsql implements SubmissionRepository {

    private Logger logger;
    public SubmissionRepositoryPsql() {
        super();
    }

    @Override
    public void insertSubmission(Submission submission) {

    }

    @Override
    public void updateSubmission(Submission submission) {

    }

    @Override
    public void deleteSubmission(long id) {

    }

    @Override
    public Submission fetchSubmission(long id) {
        return null;
    }

    @Override
    public List<Submission> fetchSubmissions(Predicate<Submission> predicate) {
        return null;
    }
}
