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

    /**
     * The {@code Logger} instance to be used in this class.
     */
    @Inject
    private transient SerializableLogger logger;


    /**
     * The pagination DTO.
     */
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
     * Loads the data for the pagination.
     */
    public abstract String loadData();

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
     * Filters the pagination by the search string.
     *
     * @return The navigation outcome.
     */
    public String filterBy() {
        logger.finest("Searching for" + pagination.getSearchString());
        return loadData();
    }

    /**
     * Navigates to the next page.
     *
     * @return The navigation outcome.
     */
    public String nextPage() {
        pagination.setCurrentIndex(pagination.getCurrentIndex() + 1);
        return loadData();
    }

    /**
     * Navigates to the previous page.
     *
     * @return The navigation outcome.
     */
    public String previousPage() {
        pagination.setCurrentIndex(pagination.getCurrentIndex() - 1);
        return loadData();
    }

    /**
     * Navigates to the first page.
     *
     * @return The navigation outcome.
     */
    public String firstPage() {
        pagination.setCurrentIndex(1);
        return loadData();
    }

    /**
     * Navigates to the last page.
     *
     * @return The navigation outcome.
     */
    public String lastPage() {
        pagination.setCurrentIndex(pagination.getLastIndex());
        return loadData();
    }

    /**
     * Navigates to a specific page of the pagination.
     *
     * @return The navigation outcome.
     */
    public String selectedPage() {
        return loadData();
    }

    /**
     * Sorts the pagination by the given sortBy attribute.
     *
     * @param sortBy The sortBy attribute.
     * @return The navigation outcome.
     */
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
     * Returns if the previous button is enabled.
     *
     * @return {@code true} if the previous button is enabled otherwise {@code false}.
     */
    public boolean prevButtonEnabled() {
        return pagination.getCurrentIndex() > 1;
    }

    /**
     * Returns if the next button is enabled.
     *
     * @return {@code true} if the next button is enabled otherwise {@code false}.
     */
    public boolean nextButtonEnabled() {
        return pagination.getCurrentIndex() < pagination.getLastIndex();
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


}
