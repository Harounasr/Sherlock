package de.ssherlock.global.transport;

import java.awt.*;

public record ExerciseDescriptionImage(
        long id,
        long exerciseId,
        byte[] image
) {
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
