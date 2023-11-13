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
    public static class Builder {
        private long id;
        private String name;
        private Date publishDate;
        private Date recommendedDeadline;
        private Date obligatoryDeadline;
        private List<Long> checkerIds;

        public Builder() {
        }

        public Builder copyFrom(Exercise exercise) {
            this.id = exercise.id();
            this.name = exercise.name();
            this.publishDate = exercise.publishDate();
            this.recommendedDeadline = exercise.recommendedDeadline();
            this.obligatoryDeadline = exercise.obligatoryDeadline();
            this.checkerIds = exercise.checkerIds();
            return this;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder publishDate(Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Builder recommendedDeadline(Date recommendedDeadline) {
            this.recommendedDeadline = recommendedDeadline;
            return this;
        }

        public Builder obligatoryDeadline(Date obligatoryDeadline) {
            this.obligatoryDeadline = obligatoryDeadline;
            return this;
        }

        public Builder checkerIds(List<Long> checkerIds) {
            this.checkerIds = checkerIds;
            return this;
        }

        public Exercise build() {
            return new Exercise(id, name, publishDate, recommendedDeadline, obligatoryDeadline, checkerIds);
        }
    }
}
