package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.TestateComment;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public interface SubmissionFileRepository {

    void insertSubmissionFile(long submissionId, File file);
    void updateSubmissionFile(long id, File file);
    void deleteSubmissionFile(long id);
    File fetchSubmissionFile(long id);
    List<File> fetchTestateComments(Predicate<File> predicate);

}
