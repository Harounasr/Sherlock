package de.ssherlock.persistence.transaction;

import java.util.Objects;
import java.util.logging.Logger;

public class ConnectionPoolPsql implements ConnectionPool {

    private ConnectionPoolPsql INSTANCE;
    private Logger logger;

    private ConnectionPoolPsql() {

    }

    @Override
    public ConnectionPool getInstance() {
        return Objects.requireNonNullElseGet(INSTANCE, ConnectionPoolPsql::new);
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getConnection() {

    }

    @Override
    public void releaseConnection() {

    }
}
