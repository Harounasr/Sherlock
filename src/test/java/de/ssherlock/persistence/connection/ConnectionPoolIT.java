package de.ssherlock.persistence.connection;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.Configuration;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConnectionPoolIT {

    /**
     * Mock servlet context event.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private ServletContextEvent servletContextEvent;

    /**
     * Mock servlet context.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private ServletContext servletContext;

    /**
     * The connection pool to test.
     */
    private ConnectionPool connectionPool;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        LoggerCreator.readConfig(servletContextEvent);

        Configuration configuration = new Configuration();

        Field configLoggerField = Configuration.class.getDeclaredField("logger");
        configLoggerField.setAccessible(true);
        configLoggerField.set(configuration, LoggerCreator.get(Configuration.class));

        Field configField = ConnectionPool.class.getDeclaredField("configuration");
        configField.setAccessible(true);
        connectionPool = new ConnectionPool();
        configField.set(connectionPool, configuration);

        Field loggerField = ConnectionPool.class.getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(connectionPool, LoggerCreator.get(ConnectionPool.class));

        configuration.init(servletContextEvent);

    }

    @Test
    void testInitialization() {
        assertDoesNotThrow(() -> connectionPool.init());
    }
}
