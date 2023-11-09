package de.ssherlock.global.transport;

public record CheckerResult (
        Checker checker,
        boolean passed,
        String stackTrace
) {
}
