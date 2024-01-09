package de.ssherlock;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class StartEmbeddedDatabase {

    private static final String CREATE_DATABASE_LOCATION = "de/ssherlock/test_data/create_database.sql";
    private static final String CREATE_SCHEME_LOCATION = "de/ssherlock/test_data/create_scheme.sql";
    private static final String TESTDATA_LOCATION = "de/ssherlock/test_data/testData.sql";

    public static void main(String[] args) throws SQLException, IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().setPort(5432).start();
        executeSqlScript(embeddedPostgres, CREATE_DATABASE_LOCATION);
        executeSqlScript(embeddedPostgres, CREATE_SCHEME_LOCATION);
        executeSqlScript(embeddedPostgres, TESTDATA_LOCATION);
        System.out.println("Database Setup complete.");
    }

    private static void executeSqlScript(EmbeddedPostgres embeddedPostgres, String location) throws SQLException, IOException {
        try (Connection connection = location.contains("database") ? embeddedPostgres.getPostgresDatabase().getConnection()
                : embeddedPostgres.getDatabase("postgres", "ssherlock-test-db").getConnection();
             Statement stmt = connection.createStatement()) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            if (is == null) {
                throw new IllegalArgumentException("SQL script file not found at " + location);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String sql = reader.lines().collect(Collectors.joining("\n"));
                stmt.execute(sql);
            }
        }
    }
}
