package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Checker;

import java.util.List;
import java.util.function.Predicate;

public interface CheckerRepository {
    void insertChecker(Checker checker);
    void updateChecker(Checker checker);
    void deleteChecker(long id);
    Checker fetchChecker(long id);
    List<Checker> fetchCheckers(Predicate<Checker> predicate);
}
