package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {

    private Logger logger;
    public CheckerRepositoryPsql() {
        super();
    }

    @Override
    public void insertChecker(Checker checker) {

    }

    @Override
    public void updateChecker(Checker checker) {

    }

    @Override
    public void deleteChecker(long id) {

    }

    @Override
    public Checker fetchChecker(long id) {
        return null;
    }

    @Override
    public List<Checker> fetchCheckers(Predicate<Checker> predicate) {
        return null;
    }
}
