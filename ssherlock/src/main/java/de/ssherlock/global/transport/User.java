package de.ssherlock.global.transport;

import de.ssherlock.global.authentification.SystemRole;

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
