package de.ssherlock.global.transport;

public record CheckerResult(
        Checker checker,
        boolean passed,
        String stackTrace
) {
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

