package de.ssherlock.global.transport;

public record User(
        String username,
        String email,
        String firstName,
        String lastName,
        SystemRole systemRole,
        Password password,
        String facultyName
) {
}
