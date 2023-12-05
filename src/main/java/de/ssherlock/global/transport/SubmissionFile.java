package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a SubmissionFile DTO.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class SubmissionFile implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The filename. */
  private String name;

  /** The file as a byte array. */
  private byte[] bytes;

  /** Instantiates a new Submission file. */
  public SubmissionFile() {}

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get bytes byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] getBytes() {
    return bytes;
  }

  /**
   * Sets bytes.
   *
   * @param bytes the bytes
   */
  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
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
    SubmissionFile that = (SubmissionFile) o;
    return Objects.equals(name, that.name) && Arrays.equals(bytes, that.bytes);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int result = Objects.hash(name);
    result = 31 * result + Arrays.hashCode(bytes);
    return result;
  }
}
