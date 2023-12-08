package de.ssherlock.control.backing;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Pagination;
import jakarta.inject.Inject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract class for handling Pagination events.
 *
 * @author Leon Föckersperger
 */
public abstract class AbstractPaginationBean implements Serializable {

    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private transient SerializableLogger logger;


    private Pagination pagination;

    /**
     * Default constructor.
     */
    @Inject
    public AbstractPaginationBean() {

    }

    /**
     * Initializes the backing bean for the pagination.
     * Must set the property sortBy in the pagination.
     * Otherwise, an IllegalStateException will be thrown.
     */
    public abstract void initialize();

    /**
     * Getter for the pagination.
     *
     * @return The pagination.
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Setter for the pagination.
     *
     * @param pagination The pagination.
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * Loads the data for the pagination.
     */
    public abstract String loadData();

    /**
     * Filters the pagination by the search string.
     */
    public String filterBy() {
        logger.finest("Searching for" + pagination.getSearchString());
        return loadData();
    }

    /**
     * Navigates to the next page.
     */
    public String nextPage() {
        pagination.setCurrentIndex(pagination.getCurrentIndex() + 1);
        return loadData();
    }

    /**
     * Navigates to the previous page.
     */
    public String previousPage() {
        pagination.setCurrentIndex(pagination.getCurrentIndex() - 1);
        return loadData();
    }

    /**
     * Navigates to the first page.
     */
    public String firstPage() {
        pagination.setCurrentIndex(0);
        return loadData();
    }

    /**
     * Navigates to the last page.
     */
    public String lastPage() {
        pagination.setCurrentIndex(pagination.getLastIndex());
        return loadData();
    }

    public String selectedPage() {
        return loadData();
    }

    public String sort(String sortBy) {
        logger.finest("Sorting by " + sortBy);
        if (pagination.getSortBy().equals(sortBy)) {
            logger.finest("Toggle direction ascending " + pagination.isSortAscending());
            pagination.setSortAscending(!pagination.isSortAscending());
        }
        pagination.setSortBy(sortBy);
        return loadData();
    }

    /**
     * Returns the title of the header with the sort direction if the sortableHeader is sorted by.
     *
     * @param title  The title of the header.
     * @param sortBy The sortBy attribute of the header.
     * @return The title for the header.
     */
    public String getTitleForHeader(String title, String sortBy) {
        if (pagination.getSortBy().equals(sortBy)) {
            return title + getSortDirection();
        } else {
            return title;
        }
    }

    /**
     * Returns a symbol for the sort direction dependent of the selection in the pagination.
     *
     * @return A symbol for the sort direction.
     */
    private String getSortDirection() {
        if (pagination.isSortAscending()) {
            return "↑";
        } else {
            return "↓";
        }
    }

    /**
     * Returns if the previous button is enabled.
     *
     * @return {@code true} if the previous button is enabled otherwise {@code false}.
     */
    public boolean prevButtonEnabled() {
        return pagination.getCurrentIndex() > 0;
    }

    /**
     * Returns if the next button is enabled.
     *
     * @return {@code true} if the next button is enabled otherwise {@code false}.
     */
    public boolean nextButtonEnabled() {
        return pagination.getCurrentIndex() < pagination.getLastIndex();
    }
}
