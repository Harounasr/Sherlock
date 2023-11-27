package de.ssherlock.control.backing;

/**
 * Abstract class for handling Pagination events.
 *
 * @author Leon Höfling
 */
public abstract class AbstractPaginationBean {

    /**
     * The page size of the pagination.
     */
    private int pageSize;

    /**
     * Index of the current page.
     */
    private int currentIndex;

    /**
     * Last possible index.
     */
    private int lastIndex;

    /**
     * The String to filter by.
     */
    private String searchString;

    /**
     * Loads the data for the pagination.
     */
    public abstract void loadData();

    /**
     * Filters the pagination by the search string.
     */
    public abstract void filterBy();

    /**
     * Navigates to the next page.
     */
    public void nextPage() {
        if (currentIndex + pageSize - 1 < lastIndex) {
            currentIndex += pageSize;
            loadData();
        }
    }

    /**
     * Navigates to the previous page.
     */
    public void previousPage() {
        if (currentIndex > 0) {
            currentIndex -= pageSize;
            loadData();
        }
    }

    /**
     * Navigates to the first page.
     */
    public void firstPage() {
        currentIndex = 0;
        loadData();
    }

    /**
     * Navigates to the last page.
     */
    public void lastPage() {
        currentIndex = lastIndex;
        loadData();
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets current page.
     *
     * @return the current page
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Sets current page.
     *
     * @param currentIndex the current page
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * Gets last index.
     *
     * @return the last index
     */
    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * Sets last index.
     *
     * @param lastIndex the last index
     */
    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    /**
     * Gets search string.
     *
     * @return the search string
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Sets search string.
     *
     * @param searchString the search string
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
