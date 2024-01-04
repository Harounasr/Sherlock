package de.ssherlock.persistence.config;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.exception.ConfigNotReadableException;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link Configuration}.
 *
 * @author Leon Höfling
 */
@ExtendWith(MockitoExtension.class)
public class ConfigurationIT {

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
     * Mock serializable logger.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private SerializableLogger logger;

    /**
     * Mock broken input stream.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private InputStream brokenInputStream;

    /** The Configuration to test. */
    private Configuration configuration;

    /**
     * Test for initialization of a configuration using the init() method.
     */
    @Test
    void testInitialization() {
        configuration = new Configuration(logger);
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<Configuration> clazz = Configuration.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        configuration.init(servletContextEvent);
        assertNotNull(configuration.getDbDriver());
        assertNotNull(configuration.getMailFrom());
        assertNotNull(configuration.getDbHost());
        assertNotNull(configuration.getDbName());
        assertNotNull(configuration.getMailPort());
        assertNotNull(configuration.getMailHost());
        assertNotNull(configuration.getMailPassword());
        assertNotEquals(0, configuration.getDbNumConnections());
        assertNotEquals(0, configuration.getDbTimeoutMillis());
        assertNotNull(configuration.getDbConnectionPrefix());
    }

    /**
     * Test for reading a valid config file.
     * Should not throw any exceptions.
     */
    @Test
    void testReadConfigFile() {
        configuration = new Configuration(logger);
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<Configuration> clazz = Configuration.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        assertDoesNotThrow(() -> configuration.init(servletContextEvent));
    }

    /**
     * Test for reading a non-existing configuration file.
     * Should throw a {@link ConfigNotReadableException}.
     */
    @Test
    void testReadConfigFileThrows() {
        configuration = new Configuration(logger);
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            Class<Configuration> clazz = Configuration.class;
            return clazz.getResourceAsStream("NONE");
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        Exception exception = assertThrows(ConfigNotReadableException.class, () -> configuration.init(servletContextEvent));
        assertEquals("The configuration file is not readable.", exception.getMessage());
    }

    /**
     * Test for reading a configuration file with a broken input stream.
     * Should throw a {@link ConfigNotReadableException}.
     *
     * @throws IOException can be ignored.
     */
    @Test
    void testReadConfigFileThrowsBrokenProperties() throws IOException {
        configuration = new Configuration(logger);
        when(brokenInputStream.read(any())).thenThrow(new IOException("Simulated IOException."));
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> brokenInputStream);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        Exception exception = assertThrows(ConfigNotReadableException.class, () -> configuration.init(servletContextEvent));
        assertEquals("Error reading the configuration file.", exception.getMessage());
    }

}
