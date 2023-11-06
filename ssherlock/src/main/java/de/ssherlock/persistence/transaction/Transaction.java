package de.ssherlock.persistence.transaction;

public interface Transaction extends AutoCloseable {

    void commit();

    void abort();

}
