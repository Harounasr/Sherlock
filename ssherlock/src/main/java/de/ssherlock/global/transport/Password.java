package de.ssherlock.global.transport;

public record Password(
        String hash,
        String salt
) {
}
