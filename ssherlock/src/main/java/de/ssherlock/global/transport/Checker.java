package de.ssherlock.global.transport;

public record Checker(
        String name,
        String parameterOne,
        String parameterTwo,
        boolean mandatory,
        boolean visible,
        long id
) {
    public class Builder {
        private String name;
        private String parameterOne;
        private String parameterTwo;
        private boolean mandatory;
        private boolean visible;
        private long id;
        public Builder() {

        }

        public Builder copyFrom(Checker checker) {
            this.name = checker.name();
            this.parameterOne = checker.parameterOne();
            this.parameterTwo = checker.parameterTwo();
            this.mandatory = checker.mandatory();
            this.visible = checker.visible();
            this.id = checker.id();
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parameterOne(String parameterOne) {
            this.parameterOne = parameterOne;
            return this;
        }

        public Builder parameterTwo(String parameterTwo) {
            this.parameterTwo = parameterTwo;
            return this;
        }

        public Builder mandatory(boolean mandatory) {
            this.mandatory = mandatory;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Checker build() {
            return new Checker(name, parameterOne, parameterTwo, mandatory, visible, id);
        }
    }
}
