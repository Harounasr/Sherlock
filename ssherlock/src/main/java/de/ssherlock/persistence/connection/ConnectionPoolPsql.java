package de.ssherlock.persistence.connection;

import java.util.logging.Logger;

public class ConnectionPoolPsql implements ConnectionPool {

    private ConnectionPoolPsql INSTANCE;
    private Logger logger;

    private ConnectionPoolPsql() {

    }

    @Override
    public ConnectionPool getInstance() {
        return null;
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
