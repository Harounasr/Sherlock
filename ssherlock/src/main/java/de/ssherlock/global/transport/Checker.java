package de.ssherlock.global.transport;

import java.util.Objects;

/**
 * Represents a Checker DTO.
 *
 * @param name The name of the checker.
 * @param parameterOne for Compilation and Identity Checker null,
 *                     for Spacing Checker the maximum line width,
 *                     for User Defined Checkers the input.
 * @param parameterTwo for Compilation, Identity and Spacing Checker null,
 *                     for User Defined Checkers the expected output.
 * @param mandatory Whether Checker is mandatory to pass.
 * @param visible Whether Checker is visible.
 * @param id ID of the checker.
 */
public record Checker(
        String name,
        String parameterOne,
        String parameterTwo,
        boolean mandatory,
        boolean visible,
        long id
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checker checker = (Checker) o;
        return mandatory == checker.mandatory && visible == checker.visible && id == checker.id && Objects.equals(name, checker.name) && Objects.equals(parameterOne, checker.parameterOne) && Objects.equals(parameterTwo, checker.parameterTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameterOne, parameterTwo, mandatory, visible, id);
    }

    /**
     * Builder class for constructing Checker instances.
     */
    public static class Builder {
        private String name;
        private String parameterOne;
        private String parameterTwo;
        private boolean mandatory;
        private boolean visible;
        private long id;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            // Method body intentionally left empty
        }

        /**
         * Copies attributes from an existing Checker instance.
         *
         * @param checker The Checker instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(Checker checker) {
            this.name = checker.name();
            this.parameterOne = checker.parameterOne();
            this.parameterTwo = checker.parameterTwo();
            this.mandatory = checker.mandatory();
            this.visible = checker.visible();
            this.id = checker.id();
            return this;
        }

        /**
         * Sets the name for the Checker.
         *
         * @param name The name to set.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets parameterOne for the Checker.
         *
         * @param parameterOne The parameterOne to set.
         * @return The Builder instance.
         */
        public Builder parameterOne(String parameterOne) {
            this.parameterOne = parameterOne;
            return this;
        }

        /**
         * Sets parameterTwo for the Checker.
         *
         * @param parameterTwo The parameterTwo to set.
         * @return The Builder instance.
         */
        public Builder parameterTwo(String parameterTwo) {
            this.parameterTwo = parameterTwo;
            return this;
        }

        /**
         * Sets the mandatory status for the Checker.
         *
         * @param mandatory The mandatory status to set.
         * @return The Builder instance.
         */
        public Builder mandatory(boolean mandatory) {
            this.mandatory = mandatory;
            return this;
        }

        /**
         * Sets the visibility status for the Checker.
         *
         * @param visible The visibility status to set.
         * @return The Builder instance.
         */
        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        /**
         * Sets the ID for the Checker.
         *
         * @param id The ID to set.
         * @return The Builder instance.
         */
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Builds a Checker instance using the provided attributes.
         *
         * @return The constructed Checker instance.
         */
        public Checker build() {
            return new Checker(name, parameterOne, parameterTwo, mandatory, visible, id);
        }
    }
}
