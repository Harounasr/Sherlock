package de.ssherlock.system_tests.maintenance;

import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link de.ssherlock.business.maintenance.UnusedImagesCleanEvent}.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnusedImagesCleanEventMT {

    /**
     * The UUID of the image that is not used in a description.
     */
    private static final String UNUSED_IMAGE_UUID = "4f7c5231-d22c-4d68-9b7a-1a926a37d9c3";

    /**
     * The testdata contains one used image and one unused image.
     */
    @Test
    void testCleanUnusedImages() {
        String checkImagesSql =
                """
                SELECT uuid
                FROM exercise_image;
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(checkImagesSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals(UNUSED_IMAGE_UUID, resultSet.getString("uuid"));
                if (resultSet.next()) {
                    fail("No image has been deleted");
                }
            } else {
                fail("Both images have been deleted.");
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
