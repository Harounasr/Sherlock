package main.java.de.ssherlock.global.transport;

import java.util.List;
import java.util.Map;

public record Course(
        String name,
        Map<String, CourseRole> users,
        List<String> exercises
) {
}
