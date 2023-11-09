package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.TestateComment;

import java.util.List;
import java.util.function.Predicate;

public interface TestateCommentRepository {
    void insertTestateComment(TestateComment testateComment);
    void updateTestateComment(TestateComment testateComment);
    void deleteTestateComment(long id);
    TestateComment fetchTestateComment(long id);
    List<TestateComment> fetchTestateComments(Predicate<TestateComment> predicate);

}
