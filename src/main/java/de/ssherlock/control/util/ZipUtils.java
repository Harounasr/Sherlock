package de.ssherlock.control.util;

import de.ssherlock.control.exception.ZIPNotReadableException;
import de.ssherlock.global.transport.SubmissionFile;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides utility methods for unzipping files.
 *
 * @author Leon Föckersperger
 */
public final class ZipUtils {

    /**
     * The buffer size for reading the ZIP file.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Private constructor to prevent instantiation.
     */
    private ZipUtils() {
    }

    /**
     * Unzips the provided submission archive file.
     *
     * @param zipFile The file to be unzipped.
     * @return A list of submission files representing the contents of each ZipEntry after unzipping.
     * @throws ZIPNotReadableException If the ZIP file could not be read.
     */
    public static List<SubmissionFile> unzipSubmissionArchive(Part zipFile) throws ZIPNotReadableException {
        List<SubmissionFile> results = new ArrayList<>();
        try (InputStream inputStream = zipFile.getInputStream(); ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bytesRead;
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setBytes(byteArrayOutputStream.toByteArray());
                    submissionFile.setName(entry.getName());
                    results.add(submissionFile);
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new ZIPNotReadableException("ZIP file could not be read. Cause IOException: " + e.getMessage(), e);
        }
        return results;
    }
}
