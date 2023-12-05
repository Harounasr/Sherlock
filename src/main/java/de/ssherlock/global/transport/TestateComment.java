package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a TestateComment DTO.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class TestateComment implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The ID of the file associated with this comment. */
  private long fileId;

  /** The line number of the comment in the file. */
  private int lineNumber;

  /** The comment content. */
  private String comment;

  /** Instantiates a new Testate comment. */
  public TestateComment() {}

  /**
   * Gets file id.
   *
   * @return the file id
   */
  public long getFileId() {
    return fileId;
  }

  /**
   * Sets file id.
   *
   * @param fileId the file id
   */
  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

  /**
   * Gets line number.
   *
   * @return the line number
   */
  public int getLineNumber() {
    return lineNumber;
  }

  /**
   * Sets line number.
   *
   * @param lineNumber the line number
   */
  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  /**
   * Gets comment.
   *
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets comment.
   *
   * @param comment the comment
   */
  public void setComment(String comment) {
    this.comment = comment;
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
    TestateComment that = (TestateComment) o;
    return fileId == that.fileId
        && lineNumber == that.lineNumber
        && Objects.equals(comment, that.comment);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(fileId, lineNumber, comment);
  }
}
