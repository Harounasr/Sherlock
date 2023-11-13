package de.ssherlock.global.transport;

import java.util.Objects;

public record Error(
        Exception exception,
        String message,
        String stacktrace
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(exception, error.exception) && Objects.equals(message, error.message) && Objects.equals(stacktrace, error.stacktrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exception, message, stacktrace);
    }

    public static class Builder {
        private Exception exception;
        private String message;
        private String stacktrace;

        public Builder() {
        }

        public Builder copyFrom(Error error) {
            this.exception = error.exception();
            this.message = error.message();
            this.stacktrace = error.stacktrace();
            return this;
        }

        public Builder exception(Exception exception) {
            this.exception = exception;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder stacktrace(String stacktrace) {
            this.stacktrace = stacktrace;
            return this;
        }

        public Error build() {
            return new Error(exception, message, stacktrace);
        }
    }
}
