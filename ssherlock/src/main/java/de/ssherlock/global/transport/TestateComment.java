package de.ssherlock.global.transport;

public record TestateComment(
        long fileId,
        int lineNumber,
        String comment
) {
}
