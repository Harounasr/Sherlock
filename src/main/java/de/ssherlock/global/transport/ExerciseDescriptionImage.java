package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an ExerciseDescriptionImage DTO.
 *
 * @author Leon Höfling
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
     * The UUID of the image in String format.
     */
    private String uuid;

    /**
     * The image as a byte array.
     */
    private byte[] image;

    /**
     * The id of the exercise associated with this image.
     */
    private long exerciseId;


    /**
     * Instantiates a new Exercise description image.
     */
    public ExerciseDescriptionImage() {

    }

    /**
     * Gets UUID.
     *
     * @return the UUID
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets UUID.
     *
     * @param uuid the UUID
     */
    public void setUUID(String uuid) {
        this.uuid = uuid;
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
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseDescriptionImage that = (ExerciseDescriptionImage) o;
        return uuid == that.uuid && Arrays.equals(image, that.image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(uuid);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
