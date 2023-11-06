package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Evaluation;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class EvaluationRepositoryPsql extends RepositoryPsql implements EvaluationRepository {

    private Logger logger;
    public EvaluationRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertEvaluation(Evaluation evaluation) {

    }

    @Override
    public void updateEvaluation(Evaluation evaluation) {

    }

    @Override
    public void deleteEvaluation(long id) {

    }

    @Override
    public Evaluation fetchEvaluation(long id) {
        return null;
    }

    @Override
    public List<Evaluation> fetchEvaluations(Predicate<Evaluation> predicate) {
        return null;
    }
}
