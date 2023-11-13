package de.ssherlock.global.transport;

public record SubmissionFile(
        String name,
        byte[] bytes
) {
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
