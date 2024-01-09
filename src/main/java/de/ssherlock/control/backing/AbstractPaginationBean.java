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

    /**
     * Default constructor.
     */
    public AbstractPaginationBean() {
        pagination = new Pagination();
        pagination.setCurrentIndex(0);
        pagination.setSortBy("");
        pagination.setSearchString("");
        pagination.setSortAscending(true);
    }

    /**
     * Loads the data for the pagination.
     */
    public abstract void loadData();

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
     * Navigates to the next page.
     */
    public void nextPage() {
        if (pagination.getCurrentIndex() + pagination.getPageSize() <= pagination.getLastIndex()) {
            pagination.setCurrentIndex(pagination.getCurrentIndex() + pagination.getPageSize());
        } else {
            pagination.setCurrentIndex(pagination.getLastIndex() - (pagination.getLastIndex() % pagination.getPageSize()));
        }
        loadData();
    }

    /**
     * Navigates to the previous page.
     */
    public void previousPage() {
        pagination.setCurrentIndex(Math.max(pagination.getCurrentIndex() - pagination.getPageSize(), 0));
        loadData();
    }

    /**
     * Navigates to the first page.
     */
    public void firstPage() {
        pagination.setCurrentIndex(0);
        loadData();
    }

    /**
     * Navigates to the last page.
     */
    public void lastPage() {
        pagination.setCurrentIndex(pagination.getLastIndex());
        loadData();
    }

    /**
     * Sorts the pagination by the given sortBy attribute.
     *
     * @param sortBy The sortBy attribute.
     */
    public void sort(String sortBy) {
        if (pagination.getSortBy().equals(sortBy)) {
            pagination.setSortAscending(!pagination.isSortAscending());
        }
        pagination.setSortBy(sortBy);
        loadData();
    }

    /**
     * Initializes the search.
     */
    public void search() {
        pagination.setCurrentIndex(0);
        loadData();
    }

    /**
     * Returns if the previous button is enabled.
     *
     * @return {@code true} if the previous button is enabled otherwise {@code false}.
     */
    public boolean prevButtonEnabled() {
        return pagination.getCurrentIndex() >= pagination.getPageSize();
    }

    /**
     * Returns if the next button is enabled.
     *
     * @return {@code true} if the next button is enabled otherwise {@code false}.
     */
    public boolean nextButtonEnabled() {
        return pagination.getCurrentIndex() + pagination.getPageSize() <= pagination.getLastIndex();
    }

}
