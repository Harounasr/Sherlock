package de.ssherlock.global.transport;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public record ExerciseDescriptionImage(
        long id,
        long exerciseId,
        byte[] image
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseDescriptionImage that = (ExerciseDescriptionImage) o;
        return id == that.id && exerciseId == that.exerciseId && Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, exerciseId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public static class Builder {
        private long id;
        private long exerciseId;
        private byte[] image;

        public Builder() {
        }

        public Builder copyFrom(ExerciseDescriptionImage image) {
            this.id = image.id();
            this.exerciseId = image.exerciseId();
            this.image = image.image();
            return this;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder exerciseId(long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        public ExerciseDescriptionImage build() {
            return new ExerciseDescriptionImage(id, exerciseId, image);
        }
    }
}
