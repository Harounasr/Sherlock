package de.ssherlock.global.transport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Submission DTO.
 *
 * @param exerciseId The ID of the exercise associated with this submission.
 * @param user The user who created the submission.
 * @param checkerResults The CheckerResults of the submission.
 * @param submissionFiles The files of the submission.
 * @param timestamp The time of submission.
 */
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

    /**
     * Builder class for constructing Submission instances.
     */
    public static class Builder {
        private long exerciseId;
        private String user;
        private List<CheckerResult> checkerResults;
        private List<SubmissionFile> submissionFiles;
        private Timestamp timestamp;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
        }

        /**
         * Copies attributes from an existing Submission instance.
         *
         * @param submission The Submission instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Submission submission) {
            this.exerciseId = submission.exerciseId();
            this.user = submission.user();
            this.checkerResults = submission.checkerResults();
            this.submissionFiles = submission.submissionFiles();
            this.timestamp = submission.timestamp();
            return this;
        }

        /**
         * Sets the exercise ID for the Submission.
         *
         * @param exerciseId The exercise ID to set.
         * @return The Builder instance.
         */
        public Builder exerciseId(long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        /**
         * Sets the user for the Submission.
         *
         * @param user The user to set.
         * @return The Builder instance.
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets the checker results for the Submission.
         *
         * @param checkerResults The checker results to set.
         * @return The Builder instance.
         */
        public Builder checkerResults(List<CheckerResult> checkerResults) {
            this.checkerResults = checkerResults;
            return this;
        }

        /**
         * Sets the submission files for the Submission.
         *
         * @param submissionFiles The submission files to set.
         * @return The Builder instance.
         */
        public Builder submissionFiles(List<SubmissionFile> submissionFiles) {
            this.submissionFiles = submissionFiles;
            return this;
        }

        /**
         * Sets the timestamp for the Submission.
         *
         * @param timestamp The timestamp to set.
         * @return The Builder instance.
         */
        public Builder timestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Builds a Submission instance using the provided attributes.
         *
         * @return The constructed Submission instance.
         */
        public Submission build() {
            return new Submission(exerciseId, user, checkerResults, submissionFiles, timestamp);
        }
    }
}
