package de.ssherlock;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

public class StartEmbeddedDatabase {

    /**
     * The location of the create database script.
     */
    private static final String CREATE_DATABASE_LOCATION = "de/ssherlock/test_data/create_database.sql";

    /**
     * The location of the create scheme script.
     */
    private static final String CREATE_SCHEME_LOCATION = "de/ssherlock/test_data/create_scheme.sql";

    /**
     * The location of the testdata script.
     */
    private static final String TESTDATA_LOCATION = "de/ssherlock/test_data/testData.sql";

    public static void main(String[] args) throws SQLException, IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().setPort(5432).start();
        embeddedPostgres.getDatabase("postgres", "ssherlock-test-db");
        executeSqlScript(embeddedPostgres, CREATE_DATABASE_LOCATION);
        executeSqlScript(embeddedPostgres, CREATE_SCHEME_LOCATION);
        executeSqlScript(embeddedPostgres, TESTDATA_LOCATION);
    }

    /**
     * Executes the sql script at the location.
     *
     * @param location The location of the script.
     * @throws SQLException When the sql is invalid.
     */
    private static void executeSqlScript(EmbeddedPostgres embeddedPostgres, String location) throws SQLException {
        try (Connection connection = embeddedPostgres.getPostgresDatabase().getConnection()) {
            Statement stmt = connection.createStatement();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            if (is == null) {
                throw new IllegalArgumentException("SQL script file not found.");
            }
            String sql = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            stmt.execute(sql);
        }
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "postgres");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ssherlock-test-db", properties);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
