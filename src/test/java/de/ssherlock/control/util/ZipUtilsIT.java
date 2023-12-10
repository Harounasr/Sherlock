package de.ssherlock.control.util;

import de.ssherlock.control.exception.ZIPNotReadableException;
import de.ssherlock.global.transport.SubmissionFile;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link ZipUtils}.
 *
 * @author Leon Föckersperger
 */
@ExtendWith(MockitoExtension.class)
@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"},
        justification = "Null value is handled")
public class ZipUtilsIT {

    /**
     * The path to the unpacked ZIP test data.
     */
    private static final String UNPACKED_ZIP_TESTDATA = "de/ssherlock/test_data/zip_test_data/unpacked_zip";

    /**
     * The path to the ZIP test data.
     */
    private static final String ZIP_TESTDATA = "de/ssherlock/test_data/zip_test_data/dummy.zip";


    /**
     * The expected number of files in the ZIP file.
     */
    private static final int EXPECTED_NUMBER_OF_FILES = 3;

    @Produces
    @Default
    @Mock(serializable = true)
    private static Part mockPart;

    /**
     * Tests the unzipping of a ZIP file.
     *
     * @throws IOException        If an I/O error occurs while reading the ZIP file.
     * @throws URISyntaxException If the URI of the ZIP file is invalid.
     */
    @Test
    public void testUnzipSubmissionArchive() throws IOException, URISyntaxException {
        InputStream zipStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ZIP_TESTDATA);
        Mockito.when(mockPart.getInputStream()).thenReturn(zipStream);
        assertNotNull(mockPart);
        List<SubmissionFile> result = null;
        try {
            result = ZipUtils.unzipSubmissionArchive(mockPart);
        } catch (ZIPNotReadableException e) {
            fail("IOException while unzipping");
        }
        assertNotNull(result);
        result.sort(Comparator.comparing(SubmissionFile::getName));
        assertEquals(EXPECTED_NUMBER_OF_FILES, result.size());
        List<SubmissionFile> testfiles = loadFilesFromDirectory();
        for (int i = 0; i < testfiles.size(); i++) {
            assertEquals(testfiles.get(i), result.get(i), "Element at index " + i + " should be equal");
        }
    }

    /**
     * Loads the files from the test directory.
     *
     * @return A list of submission files representing the contents of each file in the test directory.
     * @throws URISyntaxException If the URI of the test directory is invalid.
     */
    private List<SubmissionFile> loadFilesFromDirectory() throws URISyntaxException {
        List<SubmissionFile> fileList = new ArrayList<>();
        URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(UNPACKED_ZIP_TESTDATA);
        if (resourceUrl == null) {
            throw new RuntimeException("Test directory resource not found.");
        } else {
            Path path = Paths.get(resourceUrl.toURI());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        SubmissionFile subFile = new SubmissionFile();
                        subFile.setName(file.getFileName().toString());
                        subFile.setBytes(Files.readAllBytes(file));
                        fileList.add(subFile);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to load test files.");
            }
            fileList.sort(Comparator.comparing(SubmissionFile::getName));
        }
        return fileList;
    }
}
