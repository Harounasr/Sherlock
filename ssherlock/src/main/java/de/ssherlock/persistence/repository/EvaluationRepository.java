package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Evaluation;

import java.util.List;
import java.util.function.Predicate;

public interface EvaluationRepository {
    void insertEvaluation(Evaluation evaluation);
    void updateEvaluation(Evaluation evaluation);
    void deleteEvaluation(long id);
    Evaluation fetchEvaluation(long id);
    List<Evaluation> fetchEvaluations(Predicate<Evaluation> predicate);
}
