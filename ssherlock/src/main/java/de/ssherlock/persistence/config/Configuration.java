package de.ssherlock.persistence.config;

import de.ssherlock.global.logging.LoggerCreator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class Configuration {

    private final Logger logger = LoggerCreator.get(Configuration.class);

    Properties connectionProperties;
    String host;
    String driver;
    String name;
    int maxConnections;

    //MAIL
    String from;
    boolean auth;
    boolean tls;
    String mailhost;
    String port;
    String mailpassword;

    public Configuration() {
        Properties properties;
        try {
            connectionProperties = new Properties();
            properties = readConfigFile();
            connectionProperties.setProperty("user", properties.getProperty("user"));
            connectionProperties.setProperty("password", properties.getProperty("password"));
            connectionProperties.setProperty("ssl", properties.getProperty("ssl"));
            host = properties.getProperty("host");
            driver = properties.getProperty("driver");
            name = properties.getProperty("dbname");
            from = properties.getProperty("from");
            auth = Boolean.parseBoolean(properties.getProperty("auth"));
            tls = Boolean.parseBoolean(properties.getProperty("tls"));
            mailhost = properties.getProperty("mailhost");
            port = properties.getProperty("port");
            mailpassword = properties.getProperty("mailpassword");
            maxConnections = Integer.valueOf(properties.getProperty("maxConnections"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reset() {

    }

    private Properties readConfigFile() throws IOException {
        Properties prop = new Properties();
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config/config.properties");
        prop.load(stream);
        return prop;
    }

    public String getHost() {
        return host;
    }

    public String getDriver() {
        return driver;
    }

    public String getName() {
        return name;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public String getFrom() { return from; }

    public boolean getAuth() {return auth;}

    public boolean getTls() {return tls;}

    public String getMailhost() {return mailhost;}

    public String getPort() {return port;}

    public String getMailpassword() {return mailpassword;}
}
