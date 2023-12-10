package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Pagination DTO represents the pagination information for a list of items.
 * It includes information such as the current page index, the page size, the last possible index,
 * and the sorting and filtering parameters.
 *
 * @author Leon Föckersperger
 */
public class Pagination implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The page size of the pagination.
     */
    private int pageSize;

    /**
     * Index of the current page.
     */
    private int currentIndex = 1;

    /**
     * Last possible index.
     */
    private int lastIndex;

    /**
     * The String to filter by.
     */
    private String searchString;


    /**
     * The Location of a search.
     */
    private String searchLocation;
    /**
     * The sortBy of the pagination.
     */
    private String sortBy;

    /**
     * The sortAscending of the pagination.
     */
    private boolean sortAscending = true;

    /**
     * Default Constructor.
     */
    public Pagination() {

    }

    /**
     * Getter for the page size of the pagination.
     *
     * @return The page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter for the page size of the pagination.
     *
     * @param pageSize The page size to be set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter for the index of the current page.
     *
     * @return The index of the current page.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Setter for the index of the current page.
     *
     * @param currentIndex The index of the current page to be set.
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * Getter for the last possible index.
     *
     * @return The last possible index.
     */
    public int getLastIndex() {
        return lastIndex;
    }


    /**
     * Setter for the last possible index.
     *
     * @param lastIndex The last possible index to be set.
     */
    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    /**
     * Getter for the searchString of the pagination.
     *
     * @return The searchString of the pagination.
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Setter for the searchString of the pagination.
     *
     * @param searchString The searchString to be set.
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }


    /**
     * Getter for the sortBy of the pagination.
     *
     * @return The sortBy of the pagination.
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * Setter for the sortBy of the pagination.
     *
     * @param sortBy The sortBy to be set.
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Getter for the sortAscending of the pagination.
     *
     * @return The sortAscending of the pagination.
     */
    public boolean isSortAscending() {
        return sortAscending;
    }

    /**
     * Setter for the sortAscending of the pagination.
     *
     * @param sortAscending The sortAscending to be set.
     */
    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    /**
     * Getter for the searchLocation of the pagination.
     *
     * @return The searchLocation of the pagination.
     */
    public String getSearchLocation() {
        return searchLocation;
    }

    /**
     * Setter for the searchLocation of the pagination.
     *
     * @param searchLocation The searchLocation to be set.
     */
    public void setSearchLocation(String searchLocation) {
        this.searchLocation = searchLocation;
    }
}
