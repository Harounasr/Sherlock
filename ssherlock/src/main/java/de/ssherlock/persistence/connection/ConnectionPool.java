package de.ssherlock.persistence.connection;

public interface ConnectionPool {
    ConnectionPool getInstance();
    void init();
    void destroy();
    void getConnection();
    void releaseConnection();
}
