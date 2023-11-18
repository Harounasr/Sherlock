package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an ExerciseDescriptionImage DTO.
 */
@Named
@Dependent
public class ExerciseDescriptionImage implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id of the image.
     */
    private long id;

    /**
     * The id of the associated exercise.
     */
    private long exerciseId;

    /**
     * The image as a byte array.
     */
    private byte[] image;

    /**
     * Instantiates a new Exercise description image.
     */
    public ExerciseDescriptionImage() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets exercise id.
     *
     * @return the exercise id
     */
    public long getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets exercise id.
     *
     * @param exerciseId the exercise id
     */
    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseDescriptionImage that = (ExerciseDescriptionImage) o;
        return id == that.id && exerciseId == that.exerciseId && Arrays.equals(image, that.image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(id, exerciseId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
