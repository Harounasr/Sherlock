package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.Configuration;
import de.ssherlock.persistence.exception.DBUnavailableException;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link ConnectionPool}.
 *
 * @author Victor Vollmann
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("checkstyle:MagicNumber")
public class ConnectionPoolIT {

  /** The connection pool to test. */
  private ConnectionPool connectionPool;

  /** Mock configuration. */
  @Produces
  @Default
  @Mock(serializable = true)
  private Configuration mockedConfiguration;

  /** Mock configuration. */
  @Produces
  @Default
  @Mock(serializable = true)
  private Connection invalidConnection;

  /** Sets up the configuration mock and instantiates the connection pool. */
  @BeforeEach
  public void setup() throws SQLException {
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("user", "sep23g05");
    connectionProperties.setProperty("password", "Eisieleisu1e");
    connectionProperties.setProperty("ssl", "false");
    mockedConfiguration = mock(Configuration.class);
    lenient().when(mockedConfiguration.getDbNumConnections()).thenReturn(2);
    lenient().when(mockedConfiguration.getDbName()).thenReturn("sep23g05t");
    lenient().when(mockedConfiguration.getDbHost()).thenReturn("bueno.fim.uni-passau.de");
    lenient().when(mockedConfiguration.getDbTimeoutMillis()).thenReturn(5000L);
    lenient().when(mockedConfiguration.getDbDriver()).thenReturn("org.postgresql.Driver");
    lenient().when(mockedConfiguration.getConnectionProperties()).thenReturn(connectionProperties);
    lenient().when(mockedConfiguration.getDbConnectionPrefix()).thenReturn("jdbc:postgresql");
    lenient().when(invalidConnection.isValid(anyInt())).thenReturn(false);
    connectionPool =
        new ConnectionPool(mockedConfiguration, LoggerCreator.get(ConnectionPool.class));
  }

  /** Destroys the connection pool after each test. */
  @AfterEach
  public void tearDown() {
    connectionPool.destroy();
  }

  /**
   * Tests the init() method. Should not throw an exception and should have 2 connections.
   *
   * @throws Exception can be ignored.
   */
  @Test
  void testInitialization() throws Exception {
    assertDoesNotThrow(() -> connectionPool.init());
    Queue<Connection> connections = getConnectionsFromPool(connectionPool);
    assertEquals(2, connections.size());
  }

  /**
   * Tests the init() method with non-existent host. Should throw a {@link DBUnavailableException}
   * with the correct message.
   */
  @Test
  void testInitializationFailedNonExistentHost() {
    lenient().when(mockedConfiguration.getDbHost()).thenReturn("THIS:IS:NOT:A:HOST");
    Exception exception = assertThrows(DBUnavailableException.class, () -> connectionPool.init());
    assertEquals("Connection could not be created.", exception.getMessage());
  }

  /**
   * Tests the init() method with non-existent driver. Should throw a {@link DBUnavailableException}
   * with the correct message.
   */
  @Test
  void testInitializationFailedNonExistentDriver() {
    lenient().when(mockedConfiguration.getDbDriver()).thenReturn("non.existent.Driver.test");
    Exception exception = assertThrows(DBUnavailableException.class, () -> connectionPool.init());
    assertEquals("DB Driver could not be found.", exception.getMessage());
  }

  /**
   * Tests the getConnection() method. Should return a valid connection.
   *
   * @throws Exception can be ignored.
   */
  @Test
  void testGetConnection() throws Exception {
    connectionPool.init();
    Connection connection = connectionPool.getConnection();
    assertTrue(connection.isValid(5));
    Queue<Connection> connections = getConnectionsFromPool(connectionPool);
    Set<Connection> borrowedConnection = getBorrowedConnectionsFromPool(connectionPool);
    assertEquals(1, connections.size());
    assertEquals(1, borrowedConnection.size());
  }

  /**
   * Tests the getConnection() method when the connections are empty. Should throw a {@link
   * DBUnavailableException} with the correct message.
   */
  @Test
  void testGetConnectionEmpty() {
    connectionPool.init();
    connectionPool.getConnection();
    connectionPool.getConnection();
    Exception exception =
        assertThrows(DBUnavailableException.class, () -> connectionPool.getConnection());
    assertEquals(
        "No database connections currently available in the pool.", exception.getMessage());
  }

  /**
   * Tests the releaseConnection() method. Should release the connection.
   *
   * @throws Exception can be ignored.
   */
  @Test
  void testReleaseConnection() throws Exception {
    connectionPool.init();
    Queue<Connection> connections = getConnectionsFromPool(connectionPool);
    Set<Connection> borrowedConnections = getBorrowedConnectionsFromPool(connectionPool);
    Connection connection = connectionPool.getConnection();
    assertEquals(1, connections.size());
    assertEquals(1, borrowedConnections.size());
    connectionPool.releaseConnection(connection);
    assertEquals(2, connections.size());
    assertEquals(0, borrowedConnections.size());
  }

  /**
   * Tests the releaseConnection() method with a not borrowed connection. Should close the
   * connection.
   *
   * @throws Exception can be ignored.
   */
  @Test
  void testReleaseConnectionNotBorrowedConnection() throws Exception {
    connectionPool.init();
    Queue<Connection> connections = getConnectionsFromPool(connectionPool);
    Set<Connection> borrowedConnections = getBorrowedConnectionsFromPool(connectionPool);
    Connection connection = connectionPool.getConnection();
    borrowedConnections.remove(connection);
    connectionPool.releaseConnection(connection);
    assertEquals(1, connections.size());
    assertEquals(0, borrowedConnections.size());
    assertTrue(connection.isClosed());
  }

  /**
   * Tests the releaseConnection() method with an invalid connection. Should create a new connection
   * and add it to the connections queue.
   *
   * @throws Exception can be ignored.
   */
  @Test
  void testReleaseConnectionInvalidConnection() throws Exception {
    connectionPool.init();
    Set<Connection> borrowedConnections = getBorrowedConnectionsFromPool(connectionPool);
    borrowedConnections.add(invalidConnection);
    Queue<Connection> connections = getConnectionsFromPool(connectionPool);
    assertEquals(2, connections.size());
    assertEquals(1, borrowedConnections.size());
    connectionPool.releaseConnection(invalidConnection);
    assertEquals(3, connections.size());
    assertEquals(0, borrowedConnections.size());
  }

  /**
   * Gets the Queue of connections from a {@link ConnectionPool} instance.
   *
   * @param connectionPool The connection pool.
   * @return The Queue of connections.
   * @throws NoSuchFieldException When the field does not exist.
   * @throws IllegalAccessException When the access is denied.
   */
  private static Queue<Connection> getConnectionsFromPool(ConnectionPool connectionPool)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = ConnectionPool.class.getDeclaredField("connections");
    field.setAccessible(true);
    return (Queue<Connection>) field.get(connectionPool);
  }

  /**
   * Gets the List of borrowed connections from a {@link ConnectionPool} instance.
   *
   * @param connectionPool The connection pool.
   * @return The List of borrowed connections.
   * @throws NoSuchFieldException When the field does not exist.
   * @throws IllegalAccessException When the access is denied.
   */
  private static Set<Connection> getBorrowedConnectionsFromPool(ConnectionPool connectionPool)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = ConnectionPool.class.getDeclaredField("borrowedConnections");
    field.setAccessible(true);
    return (Set<Connection>) field.get(connectionPool);
  }
}
