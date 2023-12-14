package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCheckerException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for the checkerList.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class CheckerListBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger for logging within this class. */
  private final SerializableLogger logger;

  /** Active session. */
  private final AppSession appSession;

  /** Service that provides checker-based actions. */
  private final CheckerService checkerService;

  /** New Checker that can be added to the exercise. */
  private Checker newChecker;

  /** Checker Object for the update. */
  private Checker updateChecker;

  /** List of all checkers retrieved for the exercise. */
  private List<Checker> checkers;

  /** page Size. */
  private int pageSize;

  /** current Index. */
  private int currentIndex;

  /** ID of the checker to be deleted. */
  private int deleteCheckerID;

  /**
   * Constructs a CheckerListBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
   * @param checkerService The CheckerService used for managing checkers (Injected).
   */
  @Inject
  public CheckerListBean(
      SerializableLogger logger, AppSession appSession, CheckerService checkerService) {
    this.logger = logger;
    this.checkerService = checkerService;
    this.appSession = appSession;
    this.newChecker = new Checker();
    this.updateChecker = new Checker();
    System.out.println("checkerbean inittin");
  }

  /**
   * Initializes the CheckerListBean after construction. Retrieves checkers for the current exercise
   * upon creation.
   */
  @PostConstruct
  public void initialize() {
    try {
      checkers = checkerService.getChecker();
    } catch (BusinessNonExistentCheckerException e) {
      logger.log(Level.INFO, "threw this in checkerlist init");
    }
    for (Checker c : checkers) {
      System.out.println(c.getId());
    }
  }

  /** Adds the newly created checker. */
  public void addChecker() {
    checkerService.addChecker(newChecker);
  }

  /** Submits all changes. */
  public void submitChanges() {
    try {
      checkerService.updateChecker(updateChecker);
    } catch (BusinessNonExistentCheckerException e) {
      logger.log(Level.INFO, "threw this in checkerUpdate");
      return;
    }
    logger.log(Level.INFO, "updated Checker");
  }

  /**
   * Gets checkers.
   *
   * @return the checkers
   */
  public List<Checker> getCheckers() {
    return checkers;
  }

    /**
     * Deletes the Checkers.
     */
  public void deleteChecker() {
    Checker deletedChecker = new Checker();
    deletedChecker.setId(deleteCheckerID);
    checkerService.removeChecker(deletedChecker);
  }

  /**
   * Sets checkers.
   *
   * @param checkers the checkers
   */
  public void setCheckers(List<Checker> checkers) {
    this.checkers = checkers;
  }

  /**
   * Gets the new checker.
   *
   * @return the checker
   */
  public Checker getNewChecker() {
    return newChecker;
  }

  /**
   * Sets checker.
   *
   * @param checker the checker
   */
  public void setNewChecker(Checker checker) {
    this.newChecker = checker;
  }

    /**
     * Setter for the page Size.
     * @param pageSize page size
     */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

    /***
     * Getter for the page Size.
     * @return page Size
     */
  public int getPageSize() {
    return pageSize;
  }

    /***
     * Gets the current Index.
     * @return the current index.
     */
  public int getCurrentIndex() {
    return currentIndex;
  }

    /**
     * Sets the current Index.
     * @param index index.
     */
  public void setCurrentIndex(int index) {
    currentIndex = index;
  }

    /***
     * Getter for the deleteCheckerID.
     * @return the ID of the checker to be deleted.
     */
  public int getDeleteCheckerID() {
    return deleteCheckerID;
  }

    /**
     * Sette for the checkers id.
     * @param id the id.
     */
  public void setDeleteCheckerID(int id) {
    deleteCheckerID = id;
  }

    /**
     * Getter for the udpated Checker.
     * @return Checker
     */
  public Checker getUpdateChecker() {
    return updateChecker;
  }

    /**
     * Setter for the Update Checker.
     * @param checker checker to be updated.
     */
  public void setUpdateChecker(Checker checker) {
    updateChecker = checker;
  }
}
