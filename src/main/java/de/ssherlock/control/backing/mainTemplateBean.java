package de.ssherlock.control.backing;

import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;

/**
 } * Backing bean for the mainTemplateBean.xhtml facelet.
 *
 * @author Lennart Hohls.
 */
@Named
@ViewScoped
public class mainTemplateBean implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private final SerializableLogger logger;

  /**
   * Constructor for VerificationBean.
   *
   * @param logger The logger for this class.
   */
  @Inject
  public mainTemplateBean(SerializableLogger logger) {
    this.logger = logger;
  }
}
