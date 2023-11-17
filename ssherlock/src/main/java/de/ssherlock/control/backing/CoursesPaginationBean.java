package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing bean for the coursesPagination.xhtml facelet.
 */
@Named
@RequestScoped
public class CoursesPaginationBean {

    /**
     * Logger for logging within this class.
     */
    private Logger logger;

    /**
     * The active session.
     */
    private AppSession appSession;

    /**
     * Service to handle Course-related actions.
     */
    private CourseService courseService;

    /**
     * List of courses to be displayed.
     */
    private List<Course> courses;

    private int currentPage = 1;
    private int pageSize = 10;

    /**
     * Constructs a CoursesPaginationBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The CourseService (Injected).
     */
    @Inject
    public CoursesPaginationBean(Logger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    /**
     * Initializes the CoursesPaginationBean after construction.
     * Retrieves the list of courses for pagination.
     */
    @PostConstruct
    public void initialize() {
        courses = courseService.getCourses();
    }

    /**
     * Action for navigating to a specific course.
     *
     * @param e The fired ActionEvent.
     * @return A navigation outcome string of the selected course.
     */
    public String navigateToCourse(ActionEvent e) {
        return "";
    }

    /**
     * Getter for the list of courses.
     *
     * @return The list of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    public void firstPage() {
        currentPage = 0;
        loadData();
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadData();
        }
    }

    public void nextPage() {
        int lastIndex = courses.size() - 1;
        if (currentPage < lastIndex) {
            currentPage++;
            loadData();
        }
    }

    public void lastPage() {
        int lastIndex = courses.size() - 1;
        currentPage = lastIndex;
        loadData();
    }
    public void loadData() {
        courses = courseService.getCourses();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String select(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler nh = facesContext.getApplication().getNavigationHandler();

        nh.handleNavigation(facesContext, null, "/view/exercise.xhtml?faces-redirect=true&includeViewParams=true");
        logger.log(Level.INFO, "Selected " + name);
        return null; // or return a different outcome if needed
    }

}
