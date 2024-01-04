package de.ssherlock.persistence.config;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    /** The Configuration to test. */
    private Configuration configuration;

    /**
     * Test for initialization of a configuration using the init() method.
     */
    @Test
    void testInitialization() {
        configuration = new Configuration();
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
        assertFalse(configuration.isTlsEnabled());
        assertFalse(configuration.isMailAuthentication());
    }




}
