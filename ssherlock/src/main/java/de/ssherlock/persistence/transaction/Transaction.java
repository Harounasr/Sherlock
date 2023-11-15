package de.ssherlock.persistence.transaction;

import de.ssherlock.persistence.exception.DBUnavailableException;

import java.sql.SQLException;

public interface Transaction extends AutoCloseable {

    void commit() throws SQLException;

    void abort() throws SQLException;

}
