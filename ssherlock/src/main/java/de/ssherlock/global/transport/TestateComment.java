package de.ssherlock.global.transport;

import java.util.Objects;

public record TestateComment(
        long fileId,
        int lineNumber,
        String comment
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestateComment that = (TestateComment) o;
        return fileId == that.fileId && lineNumber == that.lineNumber && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, lineNumber, comment);
    }

    public static class Builder {
        private long fileId;
        private int lineNumber;
        private String comment;

        public Builder() {
        }

        public Builder copyFrom(TestateComment testateComment) {
            this.fileId = testateComment.fileId();
            this.lineNumber = testateComment.lineNumber();
            this.comment = testateComment.comment();
            return this;
        }

        public Builder fileId(long fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder lineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public TestateComment build() {
            return new TestateComment(fileId, lineNumber, comment);
        }
    }
}
