package de.ssherlock.global.transport;

public record Password(
        String hash,
        String salt
) {
    public static class Builder {
        private String hash;
        private String salt;

        public Builder() {
        }

        public Builder copyFrom(Password password) {
            this.hash = password.hash();
            this.salt = password.salt();
            return this;
        }

        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public Password build() {
            return new Password(hash, salt);
        }
    }
}
