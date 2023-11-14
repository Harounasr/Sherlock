package de.ssherlock.global.transport;

import java.util.Objects;

public record Password(
        String hash,
        String salt
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(hash, password.hash) && Objects.equals(salt, password.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, salt);
    }

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
