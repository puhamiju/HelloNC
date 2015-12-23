package com.ncsoft.mobile.hellonc.beans;

/**
 * Created by puhamiju on 2015-12-02.
 */
public class SearchItem {



    private String id[];
    private String title[];
    private String contents[];
    private String url[];
    private String thumbnail[];
    private String categoryname[];
    private String date[];


    public String[] getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String[] categoryname) {
        this.categoryname = categoryname;
    }


    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] content) {
        this.contents = contents;
    }

    public String[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }


}
