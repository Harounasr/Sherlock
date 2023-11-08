package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

@Named
@RequestScoped
public class SubmissionBean {
    @Inject
    private Logger logger;
    private ZipFile zipFile;
    private List<File> unpackedFiles;

    public SubmissionBean() {

    }

    public ZipFile getZipFile() {
        return zipFile;
    }

    public void setZipFile(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public List<File> getUnpackedFiles() {
        return unpackedFiles;
    }

    public void setUnpackedFiles(List<File> unpackedFiles) {
        this.unpackedFiles = unpackedFiles;
    }
}
