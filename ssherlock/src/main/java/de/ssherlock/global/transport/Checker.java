package de.ssherlock.global.transport;

public record Checker(
        String name,
        String parameterOne,
        String parameterTwo,
        boolean mandatory,
        boolean visible
) {
}
