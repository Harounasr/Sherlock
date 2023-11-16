package de.ssherlock.global.transport;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Exercise DTO
 *
 * @param id                  the id of the exercise.
 * @param name                The exercise name.
 * @param publishDate         The publishing date of the exercise.
 * @param recommendedDeadline The recommended deadline for submissions.
 * @param obligatoryDeadline  The obligatory deadline for submissions.
 * @param checkerIds          The ids of all checkers associated with the exercise.
 */
public record Exercise(
        long id,
        String name,
        Date publishDate,
        Date recommendedDeadline,
        Date obligatoryDeadline,
        List<Long> checkerIds
) {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id &&
                Objects.equals(name, exercise.name) &&
                Objects.equals(publishDate, exercise.publishDate) &&
                Objects.equals(recommendedDeadline, exercise.recommendedDeadline) &&
                Objects.equals(obligatoryDeadline, exercise.obligatoryDeadline) &&
                Objects.equals(checkerIds, exercise.checkerIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, publishDate, recommendedDeadline, obligatoryDeadline, checkerIds);
    }

    /**
     * Builder class for constructing Exercise instances.
     */
    public static class Builder {
        private long id;
        private String name;
        private Date publishDate;
        private Date recommendedDeadline;
        private Date obligatoryDeadline;
        private List<Long> checkerIds;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Method body intentionally left empty
        }

        /**
         * Copies attributes from an existing Exercise instance.
         *
         * @param exercise The Exercise instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Exercise exercise) {
            this.id = exercise.id();
            this.name = exercise.name();
            this.publishDate = exercise.publishDate();
            this.recommendedDeadline = exercise.recommendedDeadline();
            this.obligatoryDeadline = exercise.obligatoryDeadline();
            this.checkerIds = exercise.checkerIds();
            return this;
        }

        /**
         * Sets the ID for the Exercise.
         *
         * @param id The ID to set.
         * @return The Builder instance.
         */
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the Exercise.
         *
         * @param name The name to set.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the publish date for the Exercise.
         *
         * @param publishDate The publish date to set.
         * @return The Builder instance.
         */
        public Builder publishDate(Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        /**
         * Sets the recommended deadline for the Exercise.
         *
         * @param recommendedDeadline The recommended deadline to set.
         * @return The Builder instance.
         */
        public Builder recommendedDeadline(Date recommendedDeadline) {
            this.recommendedDeadline = recommendedDeadline;
            return this;
        }

        /**
         * Sets the obligatory deadline for the Exercise.
         *
         * @param obligatoryDeadline The obligatory deadline to set.
         * @return The Builder instance.
         */
        public Builder obligatoryDeadline(Date obligatoryDeadline) {
            this.obligatoryDeadline = obligatoryDeadline;
            return this;
        }

        /**
         * Sets the list of checker IDs for the Exercise.
         *
         * @param checkerIds The list of checker IDs to set.
         * @return The Builder instance.
         */
        public Builder checkerIds(List<Long> checkerIds) {
            this.checkerIds = checkerIds;
            return this;
        }

        /**
         * Builds an Exercise instance using the provided attributes.
         *
         * @return The constructed Exercise instance.
         */
        public Exercise build() {
            return new Exercise(id, name, publishDate, recommendedDeadline, obligatoryDeadline, checkerIds);
        }
    }
}
