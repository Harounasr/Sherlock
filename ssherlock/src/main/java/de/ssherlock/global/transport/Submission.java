package de.ssherlock.global.transport;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public record Submission(
        long exerciseId,
        String user,
        List<CheckerResult> checkerResults,
        List<SubmissionFile> submissionFiles,
        Timestamp timestamp
) {
}
