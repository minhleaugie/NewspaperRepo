package com.example.newspaperapp;

import java.util.ArrayList;

/**
 * Created by BrightLight on 1/4/2017.
 */

public class RssItem {
    private String title;
    private String description;
    private String link;
    private String date;
    private ArrayList<String> tags;

    public RssItem(){
        tags = new ArrayList<String>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addCategoryTag(String tag){
        String trimmedTag = tag.substring(tag.indexOf("[")+1,tag.indexOf("]"));
        tags.add(trimmedTag);
    }

    public ArrayList<String> getCategoryTags() { return tags; }
}
