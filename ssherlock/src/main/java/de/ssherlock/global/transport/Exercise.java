package de.ssherlock.global.transport;

import java.util.Date;
import java.util.List;

public record Exercise(
        long id,
        String name,
        Date publishDate,
        Date recommendedDeadline,
        Date obligatoryDeadline,
        List<Long> checkerIds
) {
}
