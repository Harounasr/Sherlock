package de.ssherlock.persistence.transaction;

public interface Transaction {

    void commit();
    void abort();

}
