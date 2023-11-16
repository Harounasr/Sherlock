package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents an Error DTO
 *
 * @param exception The exception that was thrown.
 * @param message The message to display.
 * @param stacktrace The stacktrace of the error.
 */
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

    /**
     * Builder class for constructing Error instances.
     */
    public static class Builder {
        private Exception exception;
        private String message;
        private String stacktrace;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Method body intentionally left empty
        }

        /**
         * Copies attributes from an existing Error instance.
         *
         * @param error The Error instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Error error) {
            this.exception = error.exception();
            this.message = error.message();
            this.stacktrace = error.stacktrace();
            return this;
        }

        /**
         * Sets the exception for the Error.
         *
         * @param exception The exception to set.
         * @return The Builder instance.
         */
        public Builder exception(Exception exception) {
            this.exception = exception;
            return this;
        }

        /**
         * Sets the message for the Error.
         *
         * @param message The message to set.
         * @return The Builder instance.
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the stack trace for the Error.
         *
         * @param stacktrace The stack trace to set.
         * @return The Builder instance.
         */
        public Builder stacktrace(String stacktrace) {
            this.stacktrace = stacktrace;
            return this;
        }

        /**
         * Builds an Error instance using the provided attributes.
         *
         * @return The constructed Error instance.
         */
        public Error build() {
            return new Error(exception, message, stacktrace);
        }
    }
}
