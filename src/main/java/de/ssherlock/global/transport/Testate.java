package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Testate DTO.
 *
 * @author Victor Vollmann
 */
public class Testate implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The username of the evaluator.
     */
    private long evaluatorId;

    /**
     * The username of the student.
     */
    private String student;

    /**
     * The functionality grade.
     */
    private int functionalityGrade;

    /**
     * The readability grade.
     */
    private int readabilityGrade;

    /**
     * The layout grade.
     */
    private int layoutGrade;

    /**
     * The structure grade.
     */
    private int structureGrade;

    /**
     * List of TestateComments.
     */
    private List<TestateComment> comments;

    /**
     * Comment on the testate.
     */
    private String comment;

    /**
     * The submission associated with the testate.
     */
    private Submission submission;

    /**
     * Instantiates a new Testate.
     */
    public Testate() {}

    /**
     * Gets evaluatorId.
     *
     * @return The evaluatorId.
     */
    public long getEvaluatorId() {
        return evaluatorId;
    }

    /**
     * Sets evaluatorId.
     *
     * @param evaluatorId Id of the evaluator.
     */
    public void setEvaluatorId(long evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    /**
     * Gets student.
     *
     * @return the student
     */
    public String getStudent() {
        return student;
    }

    /**
     * Sets student.
     *
     * @param student the student
     */
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * Gets functionality grade.
     *
     * @return the functionality grade
     */
    public int getFunctionalityGrade() {
        return functionalityGrade;
    }

    /**
     * Sets functionality grade.
     *
     * @param functionalityGrade the functionality grade
     */
    public void setFunctionalityGrade(int functionalityGrade) {
        this.functionalityGrade = functionalityGrade;
    }

    /**
     * Gets readability grade.
     *
     * @return the readability grade
     */
    public int getReadabilityGrade() {
        return readabilityGrade;
    }

    /**
     * Sets readability grade.
     *
     * @param readabilityGrade the readability grade
     */
    public void setReadabilityGrade(int readabilityGrade) {
        this.readabilityGrade = readabilityGrade;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public List<TestateComment> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(List<TestateComment> comments) {
        this.comments = comments;
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

    /**
     * Gets submission.
     *
     * @return the submission
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Sets submission.
     *
     * @param submission the submission
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * Gets the layout grade.
     *
     * @return The layout grade,
     */
    public int getLayoutGrade() {
        return layoutGrade;
    }

    /**
     * Sets the layout grade.
     *
     * @param layoutGrade The layout grade to set.
     */
    public void setLayoutGrade(int layoutGrade) {
        this.layoutGrade = layoutGrade;
    }

    /**
     * Gets the structure grade.
     *
     * @return The structure grade.
     */
    public int getStructureGrade() {
        return structureGrade;
    }

    /**
     * Sets the structure grade.
     *
     * @param structureGrade The structure grade to set.
     */
    public void setStructureGrade(int structureGrade) {
        this.structureGrade = structureGrade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Testate testate = (Testate) o;
        return functionalityGrade == testate.functionalityGrade
               && readabilityGrade == testate.readabilityGrade
               && Objects.equals(evaluatorId, testate.evaluatorId)
               && Objects.equals(student, testate.student)
               && Objects.equals(comments, testate.comments)
               && Objects.equals(comment, testate.comment)
               && Objects.equals(submission, testate.submission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                evaluatorId, student, functionalityGrade, readabilityGrade, comments, comment, submission);
    }
}
