package de.ssherlock.persistence.config;

import de.ssherlock.global.logging.LoggerCreator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfiguration {

    private static final Logger logger = LoggerCreator.get(DatabaseConfiguration.class);

    static DatabaseConfiguration INSTANCE;
    Properties connectionProperties;
    String host;
    String driver;
    String name;
    int maxConnections;


    private DatabaseConfiguration() {

    }

    public static DatabaseConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConfiguration();
        }
        return INSTANCE;
    }
    public void init(Function<String, InputStream> resourceFetcher) {
        Properties properties;
        try {
            connectionProperties = new Properties();
            properties = readConfigFile(resourceFetcher);
            connectionProperties.setProperty("user", properties.getProperty("user"));
            connectionProperties.setProperty("password", properties.getProperty("password"));
            connectionProperties.setProperty("ssl", properties.getProperty("ssl"));
            host = properties.getProperty("host");
            driver = properties.getProperty("driver");
            name = properties.getProperty("dbname");
            maxConnections = Integer.parseInt(properties.getProperty("maxConnections"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reset() {

    }


    private Properties readConfigFile(Function<String, InputStream> resourceFetcher) throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = resourceFetcher.apply("/WEB-INF/config/database-config.properties");
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
}
