package main.java.de.ssherlock.global.transport;

public record Submission(
        String user,
        String tutor,
        int grade,
        long testId
) {
}
