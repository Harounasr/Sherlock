package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a SystemSettings DTO.
 *
 * @author Lennart Hohls
 */
public class SystemSettings implements Serializable {

    /** Serial Version UID. */
    @Serial private static final long serialVersionUID = 1L;

    /** The regular expression for email validation. */
    private String emailRegex;

    /** The systems primary color. */
    private String primaryColorHex;

    /** The systems secondary color. */
    private String secondaryColorHex;

    /** The name of the system. */
    private String systemName;

    /** The logo of the system. */
    private byte[] logo;

    /** A list of faculties on the system. */
    private List<String> faculties;

    /** The imprint text displayed in the footer.*/
    private String imprint;

    /** The  contact_information displayed in the footer.*/
    private String contactInformation;

    /** Instantiates new System settings. */
    public SystemSettings() {}

    /**
     * Gets email regex.
     *
     * @return the email regex
     */
    public String getEmailRegex() {
        return emailRegex;
    }

    /**
     * Sets email regex.
     *
     * @param emailRegex the email regex.
     */
    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

    /**
     * Gets primary color hex.
     *
     * @return the primary color hex
     */
    public String getPrimaryColorHex() {
        return primaryColorHex;
    }

    /**
     * Sets primary color hex.
     *
     * @param primaryColorHex the primary color hex
     */
    public void setPrimaryColorHex(String primaryColorHex) {
        this.primaryColorHex = primaryColorHex;
    }

    /**
     * Gets secondary color hex.
     *
     * @return the secondary color hex
     */
    public String getSecondaryColorHex() {
        return secondaryColorHex;
    }

    /**
     * Sets secondary color hex.
     *
     * @param secondaryColorHex the secondary color hex
     */
    public void setSecondaryColorHex(String secondaryColorHex) {
        this.secondaryColorHex = secondaryColorHex;
    }

    /**
     * Gets system name.
     *
     * @return the system name
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Sets system name.
     *
     * @param systemName the system name
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * Get logo byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getLogo() {
        return logo;
    }

    /**
     * Sets logo.
     *
     * @param logo the logo
     */
    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    /**
     * Gets faculties.
     *
     * @return the faculties
     */
    public List<String> getFaculties() {
        return faculties;
    }

    /** Getter for the imprint.
     * @return imprint*/
    public String getImprint() {
        return imprint;
    }

    /** Setter for the imprint.
     * @param imprint the imprint text */
    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    /** Sets the contactInformation.
     *
     * @param contactInformation the contactInformation text.
     */
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /** Gets the contactInformation.
     *
     * @return contactInformation
     */
    public String getContactInformation() {
        return this.contactInformation;
    }

    /**
     * Sets faculties.
     *
     * @param faculties the faculties
     */
    public void setFaculties(List<String> faculties) {
        this.faculties = faculties;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemSettings that = (SystemSettings) o;
        return Objects.equals(emailRegex, that.emailRegex)
               && Objects.equals(primaryColorHex, that.primaryColorHex)
               && Objects.equals(secondaryColorHex, that.secondaryColorHex)
               && Objects.equals(systemName, that.systemName)
               && Arrays.equals(logo, that.logo)
               && Objects.equals(faculties, that.faculties);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        int result =
                Objects.hash(emailRegex, primaryColorHex, secondaryColorHex, systemName, faculties);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }
}

