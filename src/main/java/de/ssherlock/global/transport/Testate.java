package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Testate DTO.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class Testate implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The username of the evaluator.
     */
    private String evaluator;

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
    public Testate() {

    }

    /**
     * Gets evaluator.
     *
     * @return the evaluator
     */
    public String getEvaluator() {
        return evaluator;
    }

    /**
     * Sets evaluator.
     *
     * @param evaluator the evaluator
     */
    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
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
        return functionalityGrade == testate.functionalityGrade && readabilityGrade == testate.readabilityGrade && Objects.equals(evaluator,
                                                                                                                                  testate.evaluator)
               && Objects.equals(student, testate.student) && Objects.equals(comments, testate.comments) && Objects.equals(comment, testate.comment)
               && Objects.equals(submission, testate.submission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(evaluator, student, functionalityGrade, readabilityGrade, comments, comment, submission);
    }
}
