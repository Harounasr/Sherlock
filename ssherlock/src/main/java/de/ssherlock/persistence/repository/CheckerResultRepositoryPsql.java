package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.CheckerResult;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class CheckerResultRepositoryPsql implements CheckerResultRepository{

    private final Logger logger = LoggerCreator.get(CheckerResultRepositoryPsql.class);

    @Override
    public void insertCheckerResult(long submissionId, CheckerResult checkerResult) {

    }

    @Override
    public void updateCheckerResult(CheckerResult checkerResult) {

    }

    @Override
    public void deleteCheckerResult(long id) {

    }

    @Override
    public CheckerResult fetchCheckerResult(long id) {
        return null;
    }

    @Override
    public List<CheckerResult> fetchCheckers(Predicate<CheckerResult> predicate) {
        return null;
    }
}
