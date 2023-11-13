package de.ssherlock.global.transport;

import java.util.List;

public record SystemSettings(
        String emailRegex,
        String primaryColorHex,
        String secondaryColorHex,
        String systemName,
        byte[] logo,
        List<String> faculties
) {
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
