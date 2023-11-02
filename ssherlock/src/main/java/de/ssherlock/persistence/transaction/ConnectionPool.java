package de.ssherlock.persistence.transaction;

public interface ConnectionPool {
    ConnectionPool getInstance();
    void init();
    void destroy();
    void getConnection();
    void releaseConnection();
}
