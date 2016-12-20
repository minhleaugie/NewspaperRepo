package com.example.newspaperapp;

/**
 * Created by Riley on 12/20/2016.
 * Class used for separating each story into a set of XML Items
 * as the Parser runs through the page
 */

public class XMLItem {
    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String url = "";

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){this.title = title;}

    public String getLink() {
        return link;
    }
    public void setLink(String link){this.link = link;}

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){this.description = description;}

    public String getUrl() { return url;}
    public void setUrl(String url){this.url = url;}

    public String toString(){
        return title+"\n"+link+"\n"+description+"\n";
    }
}
