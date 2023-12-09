package de.ssherlock.global.logging;

import de.ssherlock.persistence.exception.ConfigNotReadableException;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link LoggerCreator} class.
 *
 * @author Victor Vollmann
 */
@ExtendWith(MockitoExtension.class)
public class LoggerCreatorIT {

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
     * Mock broken input stream.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private InputStream brokenInputStream;

    /**
     * Mock injection point.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private InjectionPoint injectionPoint;

    /**
     * Mock member.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private Member member;

    /**
     * Test for reading a valid logging config.
     * Should not throw any exceptions.
     */
    @Test
    void testReadLoggerConfig() {
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        assertDoesNotThrow(() -> LoggerCreator.readConfig(servletContextEvent));
    }

    /**
     * Test for reading a non-existing configuration.
     * Should throw a {@link ConfigNotReadableException}.
     */
    @Test
    void testReadLoggerConfigThrows() {
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream("NONE");
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        Exception exception = assertThrows(ConfigNotReadableException.class, () -> LoggerCreator.readConfig(servletContextEvent));
        assertEquals("Configuration for Logger was not found.", exception.getMessage());
    }

    /**
     * Test for reading a configuration with a broken input stream.
     * Should throw a {@link ConfigNotReadableException}.
     *
     * @throws IOException can be ignored.
     */
    @Test
    void testReadLoggerConfigThrowsBrokenProperties() throws IOException {
        when(brokenInputStream.read(any())).thenThrow(new IOException("Simulated IOException."));
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> brokenInputStream);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        Exception exception = assertThrows(ConfigNotReadableException.class, () -> LoggerCreator.readConfig(servletContextEvent));
        assertEquals("Configuration for Logger was not readable.", exception.getMessage());
    }

    /**
     * Test for getting a logger using the get() method.
     * Should return a non-null {@link SerializableLogger}.
     */
    @Test
    void testGetLogger() {
        lenient().when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream(argument);
        });
        lenient().when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        SerializableLogger logger = LoggerCreator.get(LoggerCreatorIT.class);
        assertNotNull(logger);
    }

    /**
     * Test for getting a logger using the produce() method.
     * Should return a non-null {@link SerializableLogger}.
     *
     * @throws Exception can be ignored.
     */
    @Test
    void testProduceLogger() throws Exception {
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(injectionPoint.getMember()).thenReturn(member);
        LoggerCreator.readConfig(servletContextEvent);
        Constructor<LoggerCreator> constructor = LoggerCreator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        LoggerCreator loggerCreator = constructor.newInstance();
        SerializableLogger logger = loggerCreator.produce(injectionPoint);
        assertNotNull(logger);
    }

}
