package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;

/**
 * Backing Bean for the footer.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class FooterBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The logger for this class. */
  private final SerializableLogger logger;

  /** The active session. */
  private final AppSession appSession;

  /** A SystemService instance. */
  private SystemService systemService;

  /** The imprint text. */
  private String imprint;

  /**
   * Constructs a FooterBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
   */
  @Inject
  public FooterBean(SerializableLogger logger, AppSession appSession, SystemService systemService) {
    this.logger = logger;
    this.appSession = appSession;
    this.systemService = systemService;
    setImprintText();
  }

  /**
   * Getter for the ImprintText.
   *
   * @return The Imprint text.
   */
  public String getImprint() {
    return imprint;
  }

  /**
   * Setter for the ImprintText.
   *
   * @param imprintText Text which will me set.
   */
  public void setImprint(String imprintText) {
    this.imprint = imprintText;
  }

  private void setImprintText() {
    imprint = systemService.getSystemSettings().getImprint();
  }
}
