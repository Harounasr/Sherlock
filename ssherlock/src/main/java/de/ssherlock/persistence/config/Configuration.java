package de.ssherlock.persistence.config;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.exception.ConfigNotReadableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Application-scoped configuration class for managing application settings.
 * This class reads configuration properties from a file during initialization and provides access methods for
 * retrieving specific configuration parameters.
 *
 * @author Leon Höfling
 */
@Named
@ApplicationScoped
public class Configuration implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to the Configuration class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * Properties object containing database connection properties.
     */
    private Properties connectionProperties;

    /**
     * Host address of the database.
     */
    private String dbHost;

    /**
     * JDBC driver for the database.
     */
    private String dbDriver;

    /**
     * Name of the database.
     */
    private String dbName;

    /**
     * Number of database connections.
     */
    private int dbNumConnections;

    /**
     * Timeout period for database connections in milliseconds.
     */
    private long dbTimeoutMillis;

    /**
     * Flag indicating whether SSL is enabled for database connections.
     */
    private boolean sslEnabled;

    /**
     * Email address from which emails will be sent.
     */
    private String mailFrom;

    /**
     * Password for the email account used for authentication.
     */
    private String mailPassword;

    /**
     * Flag indicating whether email authentication is required.
     */
    private boolean mailAuthentication;

    /**
     * Flag indicating whether TLS is enabled for email communication.
     */
    private boolean tlsEnabled;

    /**
     * Host address for email communication.
     */
    private String mailHost;

    /**
     * Port number for email communication.
     */
    private String mailPort;

    /**
     * Initializes the Configuration instance by reading configuration properties from a file.
     */
    public Configuration() {

    }

    public void init(ServletContextEvent sce) {
        Properties properties = readConfigFile(sce);

        connectionProperties = new Properties();
        connectionProperties.setProperty("user", properties.getProperty("DB_AUTH_USERNAME"));
        connectionProperties.setProperty("password", properties.getProperty("DB_AUTH_PASSWORD"));
        connectionProperties.setProperty("ssl", properties.getProperty("SSL_ENABLED"));
        dbHost = properties.getProperty("DB_HOST");
        dbDriver = properties.getProperty("DB_DRIVER");
        dbName = properties.getProperty("DB_NAME");
        dbNumConnections = Integer.parseInt(properties.getProperty("DB_CONNECTIONS"));
        dbTimeoutMillis = Long.parseLong(properties.getProperty("DB_TIMEOUT_MILLIS"));

        mailAuthentication = Boolean.parseBoolean(properties.getProperty("MAIL_AUTHENTICATION"));
        mailFrom = properties.getProperty("MAIL_FROM");
        mailPassword = properties.getProperty("MAIL_PASSWORD");
        tlsEnabled = Boolean.parseBoolean(properties.getProperty("TLS_ENABLED"));
        mailHost = properties.getProperty("MAIL_HOST");
        mailPort = properties.getProperty("MAIL_PORT");

        logger.log(Level.INFO, "Configuration initialized.");
    }

    /**
     * Reads configuration properties from the specified resource file.
     *
     * @return The Properties object containing configuration properties.
     * @throws ConfigNotReadableException If the configuration file is not readable.
     */
    private Properties readConfigFile(ServletContextEvent sce) {
        Properties prop = new Properties();
        InputStream dbstream = sce.getServletContext().getResourceAsStream("/WEB-INF/config/database-config.properties");
        InputStream mailstream = sce.getServletContext().getResourceAsStream("/WEB-INF/config/mail-config.properties");
        try {
            if (dbstream != null) {
                prop.load(dbstream);
            } else {
                throw new IOException("Database file not found");
            }
            if (mailstream != null) {
                prop.load(mailstream);
            } else {
                throw new IOException("Mail Config file not found");
            }
            dbstream.close();
            mailstream.close();
        } catch (IOException e) {
            throw new ConfigNotReadableException("The configuration file is not readable", e);
        }
        return prop;
    }

    /**
     * Gets the connection properties for database access.
     *
     * @return The Properties object containing database connection properties.
     */
    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    /**
     * Gets the host address of the database.
     *
     * @return The database host address.
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * Gets the JDBC driver for the database.
     *
     * @return The JDBC driver for the database.
     */
    public String getDbDriver() {
        return dbDriver;
    }

    /**
     * Gets the name of the database.
     *
     * @return The name of the database.
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Gets the number of database connections.
     *
     * @return The number of database connections.
     */
    public int getDbNumConnections() {
        return dbNumConnections;
    }

    /**
     * Gets the timeout period for database connections.
     *
     * @return The timeout period for database connections in milliseconds.
     */
    public long getDbTimeoutMillis() {
        return dbTimeoutMillis;
    }

    /**
     * Checks if SSL is enabled for database connections.
     *
     * @return True if SSL is enabled, false otherwise.
     */
    public boolean isSslEnabled() {
        return sslEnabled;
    }

    /**
     * Gets the email address from which emails will be sent.
     *
     * @return The email address from which emails will be sent.
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * Gets the password for the email account used for authentication.
     *
     * @return The password for the email account used for authentication.
     */
    public String getMailPassword() {
        return mailPassword;
    }

    /**
     * Checks if email authentication is required.
     *
     * @return True if email authentication is required, false otherwise.
     */
    public boolean isMailAuthentication() {
        return mailAuthentication;
    }

    /**
     * Checks if TLS is enabled for email communication.
     *
     * @return True if TLS is enabled, false otherwise.
     */
    public boolean isTlsEnabled() {
        return tlsEnabled;
    }

    /**
     * Gets the host address for email communication.
     *
     * @return The host address for email communication.
     */
    public String getMailHost() {
        return mailHost;
    }

    /**
     * Gets the port number for email communication.
     *
     * @return The port number for email communication.
     */
    public String getMailPort() {
        return mailPort;
    }

}
