package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Testate;

import java.util.List;
import java.util.function.Predicate;

public interface TestateRepository {
    void insertEvaluation(Testate testate);
    void updateEvaluation(Testate testate);
    void deleteEvaluation(long id);
    Testate fetchEvaluation(long id);
    List<Testate> fetchEvaluations(Predicate<Testate> predicate);
}
