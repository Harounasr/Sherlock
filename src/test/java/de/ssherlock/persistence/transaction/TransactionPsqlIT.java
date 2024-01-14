package de.ssherlock.persistence.transaction;

import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceDBAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for the {@link TransactionPsql} class.
 *
 * @author Victor Vollmann
 */
public class TransactionPsqlIT {

    /**
     * Mocked connection.
     */
    @Mock
    private Connection mockConnection;

    /**
     * Mocked connection pool.
     */
    @Mock
    private ConnectionPool mockConnectionPool;

    /**
     * Instance of the class to test.
     */
    private TransactionPsql transactionPsql;

    /**
     * Setup mocks.
     *
     * @throws PersistenceDBAccessException When the transaction can't be initiated.
     * @throws SQLException When something is wrong with the connection.
     */
    @BeforeEach
    public void setUp() throws PersistenceDBAccessException, SQLException {
        MockitoAnnotations.openMocks(this);
        doNothing().when(mockConnectionPool).releaseConnection(any(Connection.class));
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).rollback();
        doNothing().when(mockConnection).setTransactionIsolation(anyInt());
        doNothing().when(mockConnection).setAutoCommit(anyBoolean());

        transactionPsql = new TransactionPsql(mockConnection, mockConnectionPool);
    }

    /**
     * Tests the {@link TransactionPsql#commit()} method.
     *
     * @throws PersistenceDBAccessException When the transaction can't be initiated.
     * @throws SQLException When something is wrong with the connection.
     */
    @Test
    public void testCommit() throws PersistenceDBAccessException, SQLException {
        transactionPsql.commit();
        verify(mockConnection, times(1)).commit();
        verify(mockConnectionPool, times(1)).releaseConnection(mockConnection);
    }

    /**
     * Tests the {@link TransactionPsql#abort()} method.
     *
     * @throws SQLException When something is wrong with the connection.
     */
    @Test
    public void testAbort() throws SQLException {
        transactionPsql.abort();
        verify(mockConnection, times(1)).rollback();
        verify(mockConnectionPool, times(1)).releaseConnection(mockConnection);
    }

    /**
     * Tests the {@link TransactionPsql#close()} method.
     *
     * @throws SQLException When something is wrong with the connection.
     */
    @Test
    public void testClose() throws SQLException {
        transactionPsql.close();
        verify(mockConnection, times(1)).rollback();
        verify(mockConnectionPool, times(1)).releaseConnection(mockConnection);
    }

    /**
     * Tests the {@link TransactionPsql#initConnection()} method, when the
     * connection throws an SQLException on setting the auto commit.
     *
     * @throws SQLException When something is wrong with the connection.
     */
    @Test
    public void testExceptionOnInit() throws SQLException {
        doThrow(SQLException.class).when(mockConnection).setAutoCommit(anyBoolean());
        Exception exception = assertThrows(PersistenceDBAccessException.class, () -> new TransactionPsql(mockConnection, mockConnectionPool));
        assertEquals("Could not set transaction auto commit.", exception.getMessage());
    }

    /**
     * Tests the {@link TransactionPsql#getConnection()} method.
     */
    @Test
    public void testGetConnection() {
        Connection connection = transactionPsql.getConnection();
        assertEquals(mockConnection, connection);
    }

}
