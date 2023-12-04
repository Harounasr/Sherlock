package de.ssherlock.control.backing;

import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Submission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Backing bean for the allSubmissionPaginationBean.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class AllSubmissionPaginationBean extends AbstractPaginationBean implements Serializable {

  /** Serial Version UID */
  @Serial private static final long serialVersionUID = 1L;

  /** Page size for the pagination. */
  private static final int PAGE_SIZE = 10;

  /** Logger for logging within this class. */
  private final SerializableLogger logger;

  /** Active session. */
  private final AppSession appSession;

  /** Service that provides submission-based actions. */
  private final SubmissionService submissionService;

  /** List of all submissions. */
  private List<Submission> submissions;

  /**
   * Constructs an AllSubmissionPaginationBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
   * @param submissionService The SubmissionService used for submission-related actions (Injected).
   */
  @Inject
  public AllSubmissionPaginationBean(
      SerializableLogger logger, AppSession appSession, SubmissionService submissionService) {
    this.logger = logger;
    this.appSession = appSession;
    this.submissionService = submissionService;
  }

  /**
   * Initializes the AllSubmissionPaginationBean after construction. Retrieves all submissions upon
   * creation.
   */
  @PostConstruct
  public void initialize() {
    loadData();
  }

  /**
   * Action to redirect the user to the selected submission.
   *
   * @param submissionId the id of the submission.
   * @return The navigation outcome.
   */
  public String selectSubmission(long submissionId) {
    return "";
  }

  /**
   * Gets submissions.
   *
   * @return the submissions
   */
  public List<Submission> getSubmissions() {
    return submissions;
  }

  /**
   * Sets submissions.
   *
   * @param submissions the submissions
   */
  public void setSubmissions(List<Submission> submissions) {
    this.submissions = submissions;
  }

  /** {@inheritDoc} */
  @Override
  public void loadData() {
    submissions = submissionService.getSubmissions(0l);
  }

  /** {@inheritDoc} */
  @Override
  public void filterBy() {}
}
