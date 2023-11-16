package de.ssherlock.global.transport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a SystemSettings DTO.
 *
 * @param emailRegex        The regular expression for email validation.
 * @param primaryColorHex   The systems primary color.
 * @param secondaryColorHex The systems secondary color.
 * @param systemName        The name of the system.
 * @param logo              The system logo.
 * @param faculties         A list of faculties on the system.
 */
public record SystemSettings(
        String emailRegex,
        String primaryColorHex,
        String secondaryColorHex,
        String systemName,
        byte[] logo,
        List<String> faculties
) {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemSettings that = (SystemSettings) o;
        return Objects.equals(emailRegex, that.emailRegex) && Objects.equals(primaryColorHex, that.primaryColorHex) && Objects.equals(secondaryColorHex, that.secondaryColorHex) && Objects.equals(systemName, that.systemName) && Arrays.equals(logo, that.logo) && Objects.equals(faculties, that.faculties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(emailRegex, primaryColorHex, secondaryColorHex, systemName, faculties);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }

    /**
     * Builder class for constructing SystemSettings instances.
     */
    public static class Builder {
        private String emailRegex;
        private String primaryColorHex;
        private String secondaryColorHex;
        private String systemName;
        private byte[] logo;
        private List<String> faculties;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {

        }

        /**
         * Copies attributes from an existing SystemSettings instance.
         *
         * @param systemSettings The SystemSettings instance to copy from.
         * @return The Builder instance.
         */
        public Builder copyFrom(SystemSettings systemSettings) {
            this.emailRegex = systemSettings.emailRegex();
            this.primaryColorHex = systemSettings.primaryColorHex();
            this.secondaryColorHex = systemSettings.secondaryColorHex();
            this.systemName = systemSettings.systemName();
            this.logo = systemSettings.logo();
            this.faculties = systemSettings.faculties();
            return this;
        }

        /**
         * Sets the emailRegex for the SystemSettings.
         *
         * @param emailRegex The emailRegex to set.
         * @return The Builder instance.
         */
        public Builder emailRegex(String emailRegex) {
            this.emailRegex = emailRegex;
            return this;
        }

        /**
         * Sets the primaryColorHex for the SystemSettings.
         *
         * @param primaryColorHex The primaryColorHex to set.
         * @return The Builder instance.
         */
        public Builder primaryColorHex(String primaryColorHex) {
            this.primaryColorHex = primaryColorHex;
            return this;
        }

        /**
         * Sets the secondaryColorHex for the SystemSettings.
         *
         * @param secondaryColorHex The secondaryColorHex to set.
         * @return The Builder instance.
         */
        public Builder secondaryColorHex(String secondaryColorHex) {
            this.secondaryColorHex = secondaryColorHex;
            return this;
        }

        /**
         * Sets the systemName for the SystemSettings.
         *
         * @param systemName The systemName to set.
         * @return The Builder instance.
         */
        public Builder systemName(String systemName) {
            this.systemName = systemName;
            return this;
        }

        /**
         * Sets the logo for the SystemSettings.
         *
         * @param logo The logo to set.
         * @return The Builder instance.
         */
        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        /**
         * Sets the faculties for the SystemSettings.
         *
         * @param faculties The faculties to set.
         * @return The Builder instance.
         */
        public Builder faculties(List<String> faculties) {
            this.faculties = faculties;
            return this;
        }

        /**
         * Builds a SystemSettings instance using the provided attributes.
         *
         * @return The constructed SystemSettings instance.
         */
        public SystemSettings build() {
            return new SystemSettings(emailRegex, primaryColorHex, secondaryColorHex, systemName, logo, faculties);
        }
    }
}
