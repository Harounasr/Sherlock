package de.ssherlock.persistence.repository;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.Checker;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class CheckerRepositoryPsql extends RepositoryPsql implements CheckerRepository {

    private final Logger logger = LoggerCreator.get(CheckerRepositoryPsql.class);
    public CheckerRepositoryPsql(Connection connection) {
        super(connection);
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
