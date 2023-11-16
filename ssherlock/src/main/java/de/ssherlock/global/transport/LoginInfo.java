package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents a LoginInfo DTO.
 *
 * @param username The username of the user.
 * @param password The entered password.
 */
public record LoginInfo(
        String username,
        Password password
) {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInfo loginInfo = (LoginInfo) o;
        return Objects.equals(username, loginInfo.username) && Objects.equals(password, loginInfo.password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    /**
     * Builder class for constructing LoginInfo instances.
     */
    public static class Builder {
        private String username;
        private Password password;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {

        }

        /**
         * Copies attributes from an existing LoginInfo instance.
         *
         * @param loginInfo The LoginInfo instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(LoginInfo loginInfo) {
            this.username = loginInfo.username();
            this.password = loginInfo.password();
            return this;
        }

        /**
         * Sets the username for the LoginInfo.
         *
         * @param username The username to set.
         * @return The Builder instance.
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the password for the LoginInfo.
         *
         * @param password The password to set.
         * @return The Builder instance.
         */
        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        /**
         * Builds a LoginInfo instance using the provided attributes.
         *
         * @return The constructed LoginInfo instance.
         */
        public LoginInfo build() {
            return new LoginInfo(username, password);
        }
    }
}
