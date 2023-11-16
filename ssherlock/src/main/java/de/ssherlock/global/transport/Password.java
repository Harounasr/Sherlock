package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents a Password DTO.
 *
 * @param hash The hashed password.
 * @param salt The salt that was used.
 */
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

    /**
     * Builder class for constructing Password instances.
     */
    public static class Builder {
        private String hash;
        private String salt;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {

        }

        /**
         * Copies attributes from an existing Password instance.
         *
         * @param password The Password instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Password password) {
            this.hash = password.hash();
            this.salt = password.salt();
            return this;
        }

        /**
         * Sets the hash for the Password.
         *
         * @param hash The hash to set.
         * @return The Builder instance.
         */
        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        /**
         * Sets the salt for the Password.
         *
         * @param salt The salt to set.
         * @return The Builder instance.
         */
        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        /**
         * Builds a Password instance using the provided attributes.
         *
         * @return The constructed Password instance.
         */
        public Password build() {
            return new Password(hash, salt);
        }
    }
}
