package de.ssherlock.global.transport;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a SubmissionFile DTO.
 *
 * @param name The filename.
 * @param bytes The file as a byte array.
 */
public record SubmissionFile(
        String name,
        byte[] bytes
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionFile that = (SubmissionFile) o;
        return Objects.equals(name, that.name) && Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    /**
     * Builder class for constructing SubmissionFile instances.
     */
    public static class Builder {
        private String name;
        private byte[] bytes;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {

        }

        /**
         * Copies attributes from an existing SubmissionFile instance.
         *
         * @param submissionFile The SubmissionFile instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(SubmissionFile submissionFile) {
            this.name = submissionFile.name();
            this.bytes = submissionFile.bytes();
            return this;
        }

        /**
         * Sets the name for the SubmissionFile.
         *
         * @param name The name to set.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the bytes for the SubmissionFile.
         *
         * @param bytes The bytes to set.
         * @return The Builder instance.
         */
        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        /**
         * Builds a SubmissionFile instance using the provided attributes.
         *
         * @return The constructed SubmissionFile instance.
         */
        public SubmissionFile build() {
            return new SubmissionFile(name, bytes);
        }
    }
}
