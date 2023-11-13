package de.ssherlock.global.transport;

import java.util.Objects;

public record CheckerResult(
        Checker checker,
        boolean passed,
        String stackTrace
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckerResult that = (CheckerResult) o;
        return passed == that.passed && Objects.equals(checker, that.checker) && Objects.equals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checker, passed, stackTrace);
    }

    public static class Builder {
        private Checker checker;
        private boolean passed;
        private String stackTrace;

        public Builder() {
        }

        public Builder copyFrom(CheckerResult checkerResult) {
            this.checker = checkerResult.checker();
            this.passed = checkerResult.passed();
            this.stackTrace = checkerResult.stackTrace();
            return this;
        }

        public Builder checker(Checker checker) {
            this.checker = checker;
            return this;
        }

        public Builder passed(boolean passed) {
            this.passed = passed;
            return this;
        }

        public Builder stackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
            return this;
        }

        public CheckerResult build() {
            return new CheckerResult(checker, passed, stackTrace);
        }
    }
}

