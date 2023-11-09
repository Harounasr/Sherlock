package de.ssherlock.global.transport;

import java.util.List;
import java.util.Map;

public record Course(
        String name,
        Map<User, CourseRole> users,
        List<String> exercises
) {
}
