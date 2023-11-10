package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.TestateComment;
import jdk.incubator.vector.VectorOperators;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class TestateCommentRepositoryPsql implements TestateCommentRepository {

    private final Logger logger = LoggerCreator.get(TestateCommentRepositoryPsql.class);

    public TestateCommentRepositoryPsql(Connection connection) {

    }

    @Override
    public void insertTestateComment(TestateComment testateComment) {

    }

    @Override
    public void updateTestateComment(TestateComment testateComment) {

    }

    @Override
    public void deleteTestateComment(long id) {

    }

    @Override
    public TestateComment fetchTestateComment(long id) {
        return null;
    }

    @Override
    public List<TestateComment> fetchTestateComments(Predicate<TestateComment> predicate) {
        return null;
    }
}
