package de.ssherlock.persistence.config;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.exception.ConfigNotReadableException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class Configuration {

    private final Logger logger = LoggerCreator.get(Configuration.class);

    private Properties connectionProperties;
    private String dbHost;
    private String dbDriver;
    private String dbName;
    private int dbNumConnections;
    private long dbTimeoutMillis;
    private boolean sslEnabled;

    private String mailFrom;
    private String mailPassword;
    private boolean mailAuthentication;
    private boolean tlsEnabled;
    private String mailHost;
    private String mailPort;

    public Configuration() {
        Properties properties;
        properties = readConfigFile();

        connectionProperties = new Properties();
        connectionProperties.setProperty("user", properties.getProperty("DB_AUTH_USERNAME"));
        connectionProperties.setProperty("password", properties.getProperty("DB_AUTH_PASSWORD"));
        connectionProperties.setProperty("ssl", properties.getProperty("SSL_ENABLED"));
        dbHost = properties.getProperty("DB_HOST");
        dbDriver = properties.getProperty("DB_DRIVER");
        dbName = properties.getProperty("DB_NAME");
        dbNumConnections = Integer.valueOf(properties.getProperty("DB_CONNECTIONS"));
        dbTimeoutMillis = Long.valueOf(properties.getProperty("DB_TIMEOUT_MILLIS"));

        mailAuthentication = Boolean.parseBoolean(properties.getProperty("MAIL_AUTHENTICATION"));
        mailFrom = properties.getProperty("MAIL_FROM");
        mailPassword = properties.getProperty("MAIL_PASSWORD");
        tlsEnabled = Boolean.parseBoolean(properties.getProperty("TLS_ENABLED"));
        mailHost = properties.getProperty("MAIL_HOST");
        mailPort = properties.getProperty("MAIL_PORT");
    }

    private Properties readConfigFile() {
        Properties prop = new Properties();
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            throw new ConfigNotReadableException("The configuration file is not readable", e);
        }
        return prop;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbName() {
        return dbName;
    }

    public int getDbNumConnections() {
        return dbNumConnections;
    }

    public long getDbTimeoutMillis() {
        return dbTimeoutMillis;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public boolean isMailAuthentication() {
        return mailAuthentication;
    }

    public boolean isTlsEnabled() {
        return tlsEnabled;
    }

    public String getMailHost() {
        return mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }
}
