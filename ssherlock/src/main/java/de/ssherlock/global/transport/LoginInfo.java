package de.ssherlock.global.transport;

public record LoginInfo(
        String username,
        Password password
) {
    public static final class Builder {
        String username;
        Password password;

        public Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder copyFrom(LoginInfo loginInfo) {
            this.username = loginInfo.username();
            this.password = loginInfo.password();
            return this;
        }

        public LoginInfo build() {
            return new LoginInfo(username, password);
        }
    }
}
