package de.ssherlock.global.transport;

import java.io.File;
import java.util.List;

public record Submission(
        String user,
        List<CheckerResult> checkerResults,
        List<File> submissionFiles
) {
}
