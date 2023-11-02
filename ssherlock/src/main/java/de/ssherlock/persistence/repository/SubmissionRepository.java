package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Submission;

import java.util.List;
import java.util.function.Predicate;

public interface SubmissionRepository {
    void insertSubmission(Submission submission);
    void updateSubmission(Submission submission);
    void deleteSubmission(long id);
    Submission fetchSubmission(long id);
    List<Submission> fetchSubmissions(Predicate<Submission> predicate);
}
