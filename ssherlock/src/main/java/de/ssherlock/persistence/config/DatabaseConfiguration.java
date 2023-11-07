package de.ssherlock.persistence.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    static DatabaseConfiguration INSTANCE;
    Properties connectionProperties;
    String host;
    String driver;
    String name;
    int maxConnections;


    private DatabaseConfiguration() {
        Properties properties;
        try {
            properties = readConfigFile();
            connectionProperties.setProperty("user", properties.getProperty("user"));
            connectionProperties.setProperty("password", properties.getProperty("password"));
            connectionProperties.setProperty("ssl", properties.getProperty("ssl"));
            host = properties.getProperty("host");
            driver = properties.getProperty("driver");
            name = properties.getProperty("dbname");
            maxConnections = Integer.parseInt("maxConnections");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConfiguration();
        }
        return INSTANCE;
    }

    public static void reset() {

    }

    private Properties readConfigFile() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("database-config.properties");
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
