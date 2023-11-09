package de.ssherlock.global.transport;

import java.util.List;

public record Testate(
        String evaluator,
        String student,
        int functionalityGrade,
        int readabilityGrade,
        boolean finished,
        List<TestateComment> comments,
        String comment,
        Submission submission
) {
}
