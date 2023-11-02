package de.ssherlock.global.transport;

public record Evaluation(
        User evaluator,
        User student,
        int grade
) {
}
