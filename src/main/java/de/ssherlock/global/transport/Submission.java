package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Submission DTO.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class Submission implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id of the submission;
     */
    private long id;

    /**
     * The id of the exercise associated with this submission.
     */
    private long exerciseId;

    /**
     * The user who created the submission.
     */
    private String user;

    /**
     * The CheckerResults of the submission.
     */
    private List<CheckerResult> checkerResults;

    /**
     * The files of the submission.
     */
    private List<SubmissionFile> submissionFiles;

    /**
     * The time of submission.
     */
    private Timestamp timestamp;

    /**
     * Instantiates a new Submission.
     */
    public Submission() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets exercise id.
     *
     * @return the exercise id
     */
    public long getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets exercise id.
     *
     * @param exerciseId the exercise id
     */
    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets checker results.
     *
     * @return the checker results
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Sets checker results.
     *
     * @param checkerResults the checker results
     */
    public void setCheckerResults(List<CheckerResult> checkerResults) {
        this.checkerResults = checkerResults;
    }

    /**
     * Gets submission files.
     *
     * @return the submission files
     */
    public List<SubmissionFile> getSubmissionFiles() {
        return submissionFiles;
    }

    /**
     * Sets submission files.
     *
     * @param submissionFiles the submission files
     */
    public void setSubmissionFiles(List<SubmissionFile> submissionFiles) {
        this.submissionFiles = submissionFiles;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return exerciseId == that.exerciseId && Objects.equals(user, that.user) && Objects.equals(checkerResults, that.checkerResults) && Objects.equals(submissionFiles, that.submissionFiles) && Objects.equals(timestamp, that.timestamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(exerciseId, user, checkerResults, submissionFiles, timestamp);
    }
}
