package de.ssherlock.global.transport;

public record Mail (
        String email,
        String username
) {
    public static final class Builder {
        String email;
        String username;

        public Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder copyFrom(Mail mail) {
            this.username = mail.username();
            this.email = mail.email();
            return this;
        }

        public Mail build() {
            return new Mail(username, email);
        }
    }
}
