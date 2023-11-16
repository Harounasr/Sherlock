package de.ssherlock.global.transport;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Testate DTO.
 *
 * @param evaluator The username of the evaluator.
 * @param student The username of the student.
 * @param functionalityGrade The funcionality grade.
 * @param readabilityGrade The readability grade.
 * @param comments List of testate comments.
 * @param comment Comment on the testate.
 * @param submission The submission associated with the testate.
 */
public record Testate(
        String evaluator,
        String student,
        int functionalityGrade,
        int readabilityGrade,
        List<TestateComment> comments,
        String comment,
        Submission submission
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Testate testate = (Testate) o;
        return functionalityGrade == testate.functionalityGrade && readabilityGrade == testate.readabilityGrade  && Objects.equals(evaluator, testate.evaluator) && Objects.equals(student, testate.student) && Objects.equals(comments, testate.comments) && Objects.equals(comment, testate.comment) && Objects.equals(submission, testate.submission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evaluator, student, functionalityGrade, readabilityGrade, comments, comment, submission);
    }

    /**
     * Builder class for constructing Testate instances.
     */
    public static class Builder {
        private String evaluator;
        private String student;
        private int functionalityGrade;
        private int readabilityGrade;
        private List<TestateComment> comments;
        private String comment;
        private Submission submission;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Initialize default values if needed
        }

        /**
         * Copies attributes from an existing Testate instance.
         *
         * @param testate The Testate instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Testate testate) {
            this.evaluator = testate.evaluator();
            this.student = testate.student();
            this.functionalityGrade = testate.functionalityGrade();
            this.readabilityGrade = testate.readabilityGrade();
            this.comments = testate.comments();
            this.comment = testate.comment();
            this.submission = testate.submission();
            return this;
        }

        /**
         * Sets the evaluator username.
         *
         * @param evaluator The username of the evaluator.
         * @return The Builder instance.
         */
        public Builder evaluator(String evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        /**
         * Sets the student username.
         *
         * @param student The username of the student.
         * @return The Builder instance.
         */
        public Builder student(String student) {
            this.student = student;
            return this;
        }

        /**
         * Sets the functionality grade.
         *
         * @param functionalityGrade The functionality grade.
         * @return The Builder instance.
         */
        public Builder functionalityGrade(int functionalityGrade) {
            this.functionalityGrade = functionalityGrade;
            return this;
        }

        /**
         * Sets the readability grade.
         *
         * @param readabilityGrade The readability grade.
         * @return The Builder instance.
         */
        public Builder readabilityGrade(int readabilityGrade) {
            this.readabilityGrade = readabilityGrade;
            return this;
        }

        /**
         * Sets the list of testate comments.
         *
         * @param comments List of testate comments.
         * @return The Builder instance.
         */
        public Builder comments(List<TestateComment> comments) {
            this.comments = comments;
            return this;
        }

        /**
         * Sets the comment on the testate.
         *
         * @param comment Comment on the testate.
         * @return The Builder instance.
         */
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        /**
         * Sets the submission associated with the testate.
         *
         * @param submission The submission associated with the testate.
         * @return The Builder instance.
         */
        public Builder submission(Submission submission) {
            this.submission = submission;
            return this;
        }

        /**
         * Builds a Testate instance using the provided attributes.
         *
         * @return The constructed Testate instance.
         */
        public Testate build() {
            return new Testate(evaluator, student, functionalityGrade, readabilityGrade, comments, comment, submission);
        }
    }
}
