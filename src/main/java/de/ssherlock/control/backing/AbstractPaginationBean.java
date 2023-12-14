package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Pagination;

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
     * The pagination DTO.
     */
    private Pagination pagination;

    public AbstractPaginationBean() {

    }

    /**
     * Loads the data for the pagination.
     *
     * @return The navigation outcome.
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
     * Searches the pagination for the search string.
     *
     * @return The navigation outcome.
     */
    public String search() {
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
        if (pagination.getSortBy().equals(sortBy)) {
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
