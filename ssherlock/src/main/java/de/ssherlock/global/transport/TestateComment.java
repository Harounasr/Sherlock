package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents a TestateComment DTO.
 *
 * @param fileId     The id of the file associated with this comment.
 * @param lineNumber The line number of the comment.
 * @param comment    The comment content.
 */
public record TestateComment(
        long fileId,
        int lineNumber,
        String comment
) {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestateComment that = (TestateComment) o;
        return fileId == that.fileId && lineNumber == that.lineNumber && Objects.equals(comment, that.comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(fileId, lineNumber, comment);
    }

    /**
     * Builder class for constructing TestateComment instances.
     */
    public static class Builder {
        private long fileId;
        private int lineNumber;
        private String comment;

        /**
         * Initializes the builder.
         */
        public Builder() {
        }

        /**
         * Copies attributes from an existing TestateComment instance.
         *
         * @param testateComment The TestateComment instance to copy attributes from.
         * @return This builder with updated attributes.
         */
        public Builder copyFrom(TestateComment testateComment) {
            this.fileId = testateComment.fileId();
            this.lineNumber = testateComment.lineNumber();
            this.comment = testateComment.comment();
            return this;
        }

        /**
         * Sets the file ID for the TestateComment.
         *
         * @param fileId The ID of the file.
         * @return This builder with the updated file ID.
         */
        public Builder fileId(long fileId) {
            this.fileId = fileId;
            return this;
        }

        /**
         * Sets the line number for the TestateComment.
         *
         * @param lineNumber The line number.
         * @return This builder with the updated line number.
         */
        public Builder lineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        /**
         * Sets the comment content for the TestateComment.
         *
         * @param comment The comment content.
         * @return This builder with the updated comment content.
         */
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        /**
         * Builds a new TestateComment instance with the provided attributes.
         *
         * @return A new TestateComment instance.
         */
        public TestateComment build() {
            return new TestateComment(fileId, lineNumber, comment);
        }
    }
}
