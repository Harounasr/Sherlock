package de.ssherlock.global.transport;

import java.util.List;

public record Testate(
        String evaluator,
        String student,
        int functionalityGrade,
        int readabilityGrade,
        boolean finished,
        List<TestateComment> comments,
        String comment,
        Submission submission
) {
    public static class Builder {
        private String evaluator;
        private String student;
        private int functionalityGrade;
        private int readabilityGrade;
        private boolean finished;
        private List<TestateComment> comments;
        private String comment;
        private Submission submission;

        public Builder() {
        }

        public Builder copyFrom(Testate testate) {
            this.evaluator = testate.evaluator();
            this.student = testate.student();
            this.functionalityGrade = testate.functionalityGrade();
            this.readabilityGrade = testate.readabilityGrade();
            this.finished = testate.finished();
            this.comments = testate.comments();
            this.comment = testate.comment();
            this.submission = testate.submission();
            return this;
        }

        public Builder evaluator(String evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        public Builder student(String student) {
            this.student = student;
            return this;
        }

        public Builder functionalityGrade(int functionalityGrade) {
            this.functionalityGrade = functionalityGrade;
            return this;
        }

        public Builder readabilityGrade(int readabilityGrade) {
            this.readabilityGrade = readabilityGrade;
            return this;
        }

        public Builder finished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder comments(List<TestateComment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder submission(Submission submission) {
            this.submission = submission;
            return this;
        }

        public Testate build() {
            return new Testate(evaluator, student, functionalityGrade, readabilityGrade, finished, comments, comment, submission);
        }
    }
}
