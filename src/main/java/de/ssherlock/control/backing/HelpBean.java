package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the help.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@RequestScoped
public class HelpBean {

  /** The logger for this class. */
  private final SerializableLogger logger;

  /** The active session. */
  private final AppSession appSession;

  /** The message to display as help. */
  private String helpMessage;

  /**
   * Constructor for HelpBean.
   *
   * @param logger The logger instance (Injected).
   * @param appSession The active session (Injected).
   */
  @Inject
  public HelpBean(SerializableLogger logger, AppSession appSession) {
    this.logger = logger;
    this.appSession = appSession;
  }

  /**
   * Retrieves the help message.
   *
   * @return The help message.
   */
  public String getHelpMessage() {
    return helpMessage;
  }

  /**
   * Sets help message.
   *
   * @param helpMessage the help message
   */
  public void setHelpMessage(String helpMessage) {
    this.helpMessage = helpMessage;
  }
}
