package de.ssherlock.global.transport;

import java.util.Arrays;
import java.util.Objects;

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

    public static class Builder {
        private String name;
        private byte[] bytes;

        public Builder() {
        }

        public Builder copyFrom(SubmissionFile submissionFile) {
            this.name = submissionFile.name();
            this.bytes = submissionFile.bytes();
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public SubmissionFile build() {
            return new SubmissionFile(name, bytes);
        }
    }
}
