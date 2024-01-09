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

/**
 * Utility class to start an embedded database and fill it with the test data.
 * This class is supposed to be executed through {@code pom.xml} during the
 * pre-integration phase. The database is stopped automatically once the JVM
 * environment ends.
 *
 * @author Victor Vollmann
 */
public class StartEmbeddedDatabase {

    /**
     * The location of the script to create the database.
     */
    private static final String CREATE_DATABASE_LOCATION = "de/ssherlock/test_data/create_database.sql";

    /**
     * The location of the script to create the database scheme.
     */
    private static final String CREATE_SCHEME_LOCATION = "de/ssherlock/test_data/create_scheme.sql";

    /**
     * The location of the script to insert the test data.
     */
    private static final String TESTDATA_LOCATION = "de/ssherlock/test_data/testData.sql";

    /**
     * The default port for the embedded database.
     */
    private static final int EMBEDDED_DATABASE_PORT = 5432;

    /**
     * Default private constructor.
     */
    private StartEmbeddedDatabase() {

    }

    /**
     * Starts the embedded database and fills it with the test data.
     *
     * @param args Can be ignored.
     * @throws SQLException When the database cannot be started.
     * @throws IOException When the scripts cannot be found.
     */
    public static void main(String[] args) throws SQLException, IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().setPort(EMBEDDED_DATABASE_PORT).start();
        executeSqlScript(embeddedPostgres, CREATE_DATABASE_LOCATION);
        executeSqlScript(embeddedPostgres, CREATE_SCHEME_LOCATION);
        executeSqlScript(embeddedPostgres, TESTDATA_LOCATION);
        System.out.println("Database Setup complete.");
    }

    /**
     * Executes a sql script at a given location on the given embedded database.
     *
     * @param embeddedPostgres The embedded database.
     * @param location The location of the script.
     * @throws SQLException When it is not possible to connect to the database.
     * @throws IOException When the script can not be found at the specified location.
     */
    private static void executeSqlScript(EmbeddedPostgres embeddedPostgres, String location) throws SQLException, IOException {
        try (Connection connection = location.contains("create_database") ? embeddedPostgres.getPostgresDatabase().getConnection()
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
