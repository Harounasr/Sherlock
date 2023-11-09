package de.ssherlock.global.transport;

import java.util.List;

public record Testate(
        User evaluator,
        User student,
        int functionalityGrade,
        int readabilityGrade,
        boolean finished,
        List<TestateComment> comments,
        String comment
) {
}
