package de.ssherlock.global.transport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public record Submission(
        long exerciseId,
        String user,
        List<CheckerResult> checkerResults,
        List<SubmissionFile> submissionFiles,
        Timestamp timestamp
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return exerciseId == that.exerciseId && Objects.equals(user, that.user) && Objects.equals(checkerResults, that.checkerResults) && Objects.equals(submissionFiles, that.submissionFiles) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseId, user, checkerResults, submissionFiles, timestamp);
    }

    public static class Builder {
        private long exerciseId;
        private String user;
        private List<CheckerResult> checkerResults;
        private List<SubmissionFile> submissionFiles;
        private Timestamp timestamp;

        public Builder() {
        }

        public Builder copyFrom(Submission submission) {
            this.exerciseId = submission.exerciseId();
            this.user = submission.user();
            this.checkerResults = submission.checkerResults();
            this.submissionFiles = submission.submissionFiles();
            this.timestamp = submission.timestamp();
            return this;
        }

        public Builder exerciseId(long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder checkerResults(List<CheckerResult> checkerResults) {
            this.checkerResults = checkerResults;
            return this;
        }

        public Builder submissionFiles(List<SubmissionFile> submissionFiles) {
            this.submissionFiles = submissionFiles;
            return this;
        }

        public Builder timestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Submission build() {
            return new Submission(exerciseId, user, checkerResults, submissionFiles, timestamp);
        }
    }
}
