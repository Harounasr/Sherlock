package de.ssherlock.global.transport;


import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a SubmissionFile DTO.
 *
 * @author Leon Höfling
 */
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
}
