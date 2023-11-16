package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents a CheckerResult DTO.
 *
 * @param checker    The checker associated with the result.
 * @param passed     Whether the checker was passed.
 * @param stackTrace The stacktrace of the result.
 */
public record CheckerResult(
        Checker checker,
        boolean passed,
        String stackTrace
) {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckerResult that = (CheckerResult) o;
        return passed == that.passed && Objects.equals(checker, that.checker) && Objects.equals(stackTrace, that.stackTrace);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(checker, passed, stackTrace);
    }

    /**
     * Builder class for constructing CheckerResult instances.
     */
    public static class Builder {
        private Checker checker;
        private boolean passed;
        private String stackTrace;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {

        }

        /**
         * Copies attributes from an existing CheckerResult instance.
         *
         * @param checkerResult The CheckerResult instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(CheckerResult checkerResult) {
            this.checker = checkerResult.checker();
            this.passed = checkerResult.passed();
            this.stackTrace = checkerResult.stackTrace();
            return this;
        }

        /**
         * Sets the Checker for the CheckerResult.
         *
         * @param checker The Checker to set.
         * @return The Builder instance.
         */
        public Builder checker(Checker checker) {
            this.checker = checker;
            return this;
        }

        /**
         * Sets the passed status for the CheckerResult.
         *
         * @param passed The passed status to set.
         * @return The Builder instance.
         */
        public Builder passed(boolean passed) {
            this.passed = passed;
            return this;
        }

        /**
         * Sets the stack trace for the CheckerResult.
         *
         * @param stackTrace The stack trace to set.
         * @return The Builder instance.
         */
        public Builder stackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
            return this;
        }

        /**
         * Builds a CheckerResult instance using the provided attributes.
         *
         * @return The constructed CheckerResult instance.
         */
        public CheckerResult build() {
            return new CheckerResult(checker, passed, stackTrace);
        }
    }
}
