package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCheckerException;
import de.ssherlock.business.service.CheckerService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
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
@RequestScoped
public class CheckerListBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides checker-based actions.
     */
    private final CheckerService checkerService;

    /**
     * Parent Bean of the exercise.
     */
    private final ExerciseBean exerciseBean;

    /**
     * New Checker that can be added to the exercise.
     */
    private Checker newChecker;

    /**
     * List of all checkers retrieved for the exercise.
     */
    private List<Checker> checkers;

    /**
     * Type of the checker to be added.
     */
    private String currentCheckerType;

    /**
     * Page size of the pagination.
     */
    private static final int PAGE_SIZE = 10;


    /**
     * current Index.
     */
    private int currentIndex;

    /**
     * Constructs a CheckerListBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param checkerService The CheckerService used for managing checkers (Injected).
     * @param exerciseBean   the parent bean.
     */
    @Inject
    public CheckerListBean(
            SerializableLogger logger, AppSession appSession, CheckerService checkerService, ExerciseBean exerciseBean) {
        this.logger = logger;
        this.checkerService = checkerService;
        this.appSession = appSession;
        this.exerciseBean = exerciseBean;
        this.newChecker = new Checker();
        System.out.println("checkerbean inittin");
    }

    /**
     * Initializes the CheckerListBean after construction. Retrieves checkers for the current exercise
     * upon creation.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setPageSize(PAGE_SIZE);
        loadData();
        getPagination().setLastIndex(checkers.size() - 1);
        for (Checker c : checkers) {
            System.out.println(c.getId());
        }
        currentCheckerType = String.valueOf(CheckerType.USER_DEFINED);
    }

    /**
     * Adds the newly created checker.
     */
    public void addChecker() {
        newChecker.setCheckerType(CheckerType.valueOf(currentCheckerType));
        newChecker.setExerciseId(exerciseBean.getExerciseId());
        checkerService.addChecker(newChecker);
        loadData();
    }

    /**
     * Submits all changes.
     */
    public void submitChanges() {
        try {
            checkerService.updateCheckers(checkers);
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
    public void deleteChecker(int id) {
        Checker deletedChecker = new Checker();
        deletedChecker.setId(id);
        checkerService.removeChecker(deletedChecker);
        loadData();
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


    /***
     * Gets the current Index.
     * @return the current index.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Sets the current Index.
     *
     * @param index index.
     */
    public void setCurrentIndex(int index) {
        currentIndex = index;
    }


    @Override
    public void loadData() {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseBean.getExerciseId());
        try {
            checkers = checkerService.getCheckersForExercise(exercise);
        } catch (BusinessNonExistentCheckerException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CheckerType> getAllCheckerTypes() {
        return List.of(CheckerType.values());
    }

    /***
     * Getter for the current checker type.
     * @return type of added checker.
     */
    public String getCurrentCheckerType() {
        return currentCheckerType;
    }

    /**
     * Setter for the current checker type.
     *
     * @param currentCheckerType type to be setted.
     */
    public void setCurrentCheckerType(String currentCheckerType) {
        this.currentCheckerType = currentCheckerType;
    }
}
