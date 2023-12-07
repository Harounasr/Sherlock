package de.ssherlock.control.util;

import de.ssherlock.control.exception.ZIPNotReadableException;
import de.ssherlock.global.transport.SubmissionFile;
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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link ZipUtils}.
 */
@ExtendWith(MockitoExtension.class)
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
        Path path = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(UNPACKED_ZIP_TESTDATA)).toURI());
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
            fail("Unable to load test files");
        }
        fileList.sort(Comparator.comparing(SubmissionFile::getName));
        return fileList;
    }
}
