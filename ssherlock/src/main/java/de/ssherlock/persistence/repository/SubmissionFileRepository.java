package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.SubmissionFile;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public interface SubmissionFileRepository {

    void insertSubmissionFile(long submissionId, SubmissionFile file);
    void updateSubmissionFile(long submissionId, SubmissionFile file);
    void deleteSubmissionFile(long id);
    SubmissionFile fetchSubmissionFile(long id);
    List<SubmissionFile> fetchTestateComments(Predicate<SubmissionFile> predicate);

}
