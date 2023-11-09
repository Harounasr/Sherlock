package de.ssherlock.global.transport;

import java.awt.*;

public record ExerciseDescriptionImage(
        long id,
        long exerciseId,
        Image image
) {
}
