package de.ssherlock.control.util;

import de.ssherlock.global.transport.SubmissionFile;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Provides utility methods for unzipping files.
 *
 * @author Leon Höfling
 */
public final class ZipUtils {

    /**
     * Default constructor
     */
    private ZipUtils() {

    }

    /**
     * Unzips the provided submission archive file.
     *
     * @param zipFile The file to be unzipped.
     * @return A list of submission files representing the contents of each ZipEntry after unzipping.
     */
    public static List<SubmissionFile> unzipSubmissionArchive(Part zipFile) {
        File tempFile;
        ZipFile actualZipFile;
        List<SubmissionFile> results = new ArrayList<>();
        try {
            InputStream inputStream = zipFile.getInputStream();
            tempFile = File.createTempFile("temp", ".zip");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            actualZipFile = new ZipFile(tempFile);
            Enumeration<? extends ZipEntry> entries = actualZipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream entryStream = actualZipFile.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = entryStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                SubmissionFile submissionFile = new SubmissionFile();
                submissionFile.setBytes(baos.toByteArray());
                submissionFile.setName(entry.getName());
                results.add(submissionFile);
            }
            actualZipFile.close();
            tempFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

}
