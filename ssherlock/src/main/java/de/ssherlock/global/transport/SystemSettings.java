package de.ssherlock.global.transport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record SystemSettings(
        String emailRegex,
        String primaryColorHex,
        String secondaryColorHex,
        String systemName,
        byte[] logo,
        List<String> faculties
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemSettings that = (SystemSettings) o;
        return Objects.equals(emailRegex, that.emailRegex) && Objects.equals(primaryColorHex, that.primaryColorHex) && Objects.equals(secondaryColorHex, that.secondaryColorHex) && Objects.equals(systemName, that.systemName) && Arrays.equals(logo, that.logo) && Objects.equals(faculties, that.faculties);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(emailRegex, primaryColorHex, secondaryColorHex, systemName, faculties);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }

    public static class Builder {
        private String emailRegex;
        private String primaryColorHex;
        private String secondaryColorHex;
        private String systemName;
        private byte[] logo;
        private List<String> faculties;

        public Builder() {
        }

        public Builder copyFrom(SystemSettings systemSettings) {
            this.emailRegex = systemSettings.emailRegex();
            this.primaryColorHex = systemSettings.primaryColorHex();
            this.secondaryColorHex = systemSettings.secondaryColorHex();
            this.systemName = systemSettings.systemName();
            this.logo = systemSettings.logo();
            this.faculties = systemSettings.faculties();
            return this;
        }

        public Builder emailRegex(String emailRegex) {
            this.emailRegex = emailRegex;
            return this;
        }

        public Builder primaryColorHex(String primaryColorHex) {
            this.primaryColorHex = primaryColorHex;
            return this;
        }

        public Builder secondaryColorHex(String secondaryColorHex) {
            this.secondaryColorHex = secondaryColorHex;
            return this;
        }

        public Builder systemName(String systemName) {
            this.systemName = systemName;
            return this;
        }

        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public Builder faculties(List<String> faculties) {
            this.faculties = faculties;
            return this;
        }

        public SystemSettings build() {
            return new SystemSettings(emailRegex, primaryColorHex, secondaryColorHex, systemName, logo, faculties);
        }
    }
}
