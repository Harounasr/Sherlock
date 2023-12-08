package de.ssherlock.persistence.repository;

import java.sql.Connection;

/**
 * Base class for PostgreSQL repositories providing a database connection.
 *
 * @author Leon Höfling
 */
public class RepositoryPsql {

  /** The database connection. */
  private Connection connection;

  /**
   * Constructor to initialize the repository with a database connection.
   *
   * @param connection The database connection.
   */
  public RepositoryPsql(Connection connection) {
    this.connection = connection;
  }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
