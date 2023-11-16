package de.ssherlock.global.transport;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an ExerciseDescriptionImage DTO
 *
 * @param id The id of the image.
 * @param exerciseId The id of the associated exercise.
 * @param image The image as a byte array.
 */
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

    /**
     * Builder class for constructing ExerciseDescriptionImage instances.
     */
    public static class Builder {
        private long id;
        private long exerciseId;
        private byte[] image;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Initialize default values if needed
        }

        /**
         * Copies attributes from an existing ExerciseDescriptionImage instance.
         *
         * @param image The ExerciseDescriptionImage instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(ExerciseDescriptionImage image) {
            this.id = image.id();
            this.exerciseId = image.exerciseId();
            this.image = image.image();
            return this;
        }

        /**
         * Sets the ID for the ExerciseDescriptionImage.
         *
         * @param id The ID to set.
         * @return The Builder instance.
         */
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the exercise ID for the ExerciseDescriptionImage.
         *
         * @param exerciseId The exercise ID to set.
         * @return The Builder instance.
         */
        public Builder exerciseId(long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        /**
         * Sets the image data for the ExerciseDescriptionImage.
         *
         * @param image The image data to set.
         * @return The Builder instance.
         */
        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        /**
         * Builds an ExerciseDescriptionImage instance using the provided attributes.
         *
         * @return The constructed ExerciseDescriptionImage instance.
         */
        public ExerciseDescriptionImage build() {
            return new ExerciseDescriptionImage(id, exerciseId, image);
        }
    }
}
