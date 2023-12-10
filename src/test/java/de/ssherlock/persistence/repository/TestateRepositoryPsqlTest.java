package de.ssherlock.persistence.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import de.ssherlock.global.transport.Testate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@link TestateRepositoryPsql}.
 *
 * @author Haroun Alswedany
 */
@ExtendWith(MockitoExtension.class)
public class TestateRepositoryPsqlTest {

    /**
     * Test for getting testates with non-empty result set.
     *
     * @throws SQLException can be ignored.
     */
    @Test
    public void testGetTestatesWithNonEmptyResultSet() throws SQLException {
        Connection connectionMock = mock(Connection.class);

        PreparedStatement statementMock = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(any())).thenReturn(statementMock);

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("submission_id")).thenReturn(1L, 2L);
        when(resultSetMock.getInt("functionality_grade")).thenReturn(90, 85);
        when(resultSetMock.getInt("readability_grade")).thenReturn(80, 75);
        when(resultSetMock.getBytes("file")).thenReturn(new byte[]{3, 4, 5}, new byte[]{6, 7, 8});
        when(resultSetMock.getLong("file_id")).thenReturn(101L, 102L);
        when(resultSetMock.getInt("line_number")).thenReturn(10, 15);
        when(resultSetMock.getString("comment")).thenReturn("Good work", "Needs improvement");

        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        TestateRepositoryPsql testateRepository = new TestateRepositoryPsql(connectionMock);

        List<Testate> testateList = testateRepository.getTestates(1L);

        verify(connectionMock, times(1)).prepareStatement(any());
        verify(statementMock, times(1)).setLong(1, 1L);
        verify(statementMock, times(1)).executeQuery();

        assertEquals(2, testateList.size());

        assertEquals(1L, testateList.get(0).getSubmission().getId());
        assertEquals(90, testateList.get(0).getFunctionalityGrade());
        assertEquals(80, testateList.get(0).getReadabilityGrade());

        byte[] fileBytes = testateList.get(0).getSubmission().getSubmissionFiles().get(0).getBytes();
    }
}
