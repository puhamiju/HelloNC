package com.ncsoft.mobile.hellonc.beans;

/**
 * Created by puhamiju on 2015-12-03.
 */
public class SearchSet {


    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    private String collection;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private int totalCount;

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    private int searchCount;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    private String statusMessage;

    public SearchItem getResultSet() {
        return resultSet;
    }

    public void setResultSet(SearchItem resultSet) {
        this.resultSet = resultSet;
    }

    private SearchItem resultSet;
}
