package de.ssherlock.control.backing;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;

/**
 * Backing bean for the main.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class MainBean implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private final SerializableLogger logger;

  /** The text which is displayed on the verification page. */

  /**
   * Constructor for VerificationBean.
   *
   * @param logger The logger for this class.
   */
  @Inject
  public MainBean(SerializableLogger logger) {
    this.logger = logger;
  }
}
