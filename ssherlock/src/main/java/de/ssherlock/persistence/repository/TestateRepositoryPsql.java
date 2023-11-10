package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Testate;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class TestateRepositoryPsql extends RepositoryPsql implements TestateRepository {

    private final Logger logger = LoggerCreator.get(TestateRepositoryPsql.class);
    public TestateRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertEvaluation(Testate testate) {

    }

    @Override
    public void updateEvaluation(Testate testate) {

    }

    @Override
    public void deleteEvaluation(long id) {

    }

    @Override
    public Testate fetchEvaluation(long id) {
        return null;
    }

    @Override
    public List<Testate> fetchEvaluations(Predicate<Testate> predicate) {
        return null;
    }
}
