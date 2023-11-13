package de.ssherlock.global.transport;

import java.util.Objects;

public record LoginInfo(
        String username,
        Password password
) {
    public static class Builder {
        private String username;
        private Password password;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(username, builder.username) && Objects.equals(password, builder.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, password);
        }

        public Builder() {
        }

        public Builder copyFrom(LoginInfo loginInfo) {
            this.username = loginInfo.username();
            this.password = loginInfo.password();
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public LoginInfo build() {
            return new LoginInfo(username, password);
        }
    }
}
