package main.java.de.ssherlock.global.transport;

public record Checker(
        String input,
        String expectedOutput,
        boolean visibility,
        boolean obligatory
) {
}
