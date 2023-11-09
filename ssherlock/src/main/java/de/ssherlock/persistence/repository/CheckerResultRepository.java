package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;

import java.util.List;
import java.util.function.Predicate;

public interface CheckerResultRepository {
    void insertCheckerResult(CheckerResult checkerResult);
    void updateCheckerResult(CheckerResult checkerResult);
    void deleteCheckerResult(long id);
    CheckerResult fetchCheckerResult(long id);
    List<CheckerResult> fetchCheckers(Predicate<CheckerResult> predicate);
}
