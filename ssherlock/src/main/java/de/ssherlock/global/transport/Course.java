package de.ssherlock.global.transport;

import de.ssherlock.global.authentification.CourseRole;

import java.util.List;
import java.util.Map;

public record Course(
        String name,
        Map<String, CourseRole> users,
        List<String> exercises
) {
}
