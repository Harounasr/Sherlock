package main.java.de.ssherlock.global.transport;

public record User(
        String username,
        String email,
        String firstName,
        String lastName,
        SystemRole systemRole,
        String passwordHash,
        String passwordSalt,
        String facultyName
) {
}
