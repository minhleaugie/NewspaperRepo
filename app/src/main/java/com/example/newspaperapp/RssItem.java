package com.example.newspaperapp;

import java.net.MalformedURLException;
import java.net.URL;
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
    private String imageURL;
    private static int MIN_IMAGE_URL_LENGTH = (new String("http://www.augustanaobserver.com")).length();

    //Constructor; initializes variable tags
    public RssItem() {
        tags = new ArrayList<String>();
    }

    //Returns variable date
    public String getDate() {
        return date;
    }

    //Sets variable date from RSS tag pubdate
    public void setDate(String date) {
        this.date = date;
    }

    //Returns variable title
    public String getTitle() {
        return title;
    }

    //Sets variable title from RSS tag title
    public void setTitle(String title) {
        this.title = title;
    }

    //Returns variable link
    public String getLink() {
        return link;
    }

    //Sets varaible line from RSS tag link
    public void setLink(String link) {
        this.link = link;
    }

    //Returns variable description
    public String getDescription() {
        return description;
    }

    //Sets varaible description to RSS tag description
    public void setDescription(String description) {
        this.description = description;
    }

    //Adds each category tag to varaible tags
    public void addCategoryTag(String tag) { tags.add(tag); }

    //Returns variable tags
    public ArrayList<String> getCategoryTags() { return tags; }

    //Sets variable imageURL by cutting RSS string from tag encoded
    //accounts for double pictures in posts and only gets first
    public void setImageURL(String imageURL) {
        //Splits string from RSS by spaces
        String[] parts = imageURL.split("\\s+");
        // Attempt to convert each item into an URL.
        for (String item : parts) {
            //Checks if string is larger than base Observer url
            if(item.length()>MIN_IMAGE_URL_LENGTH) {
                try {
                    //Uses URL with src="...", trims off until " and final "
                    item = item.substring(5, item.length() - 1);
                    URL url = new URL(item);
                    // If possible and imageURL is null (to account for doubles), set imageURL...
                    if (this.imageURL == null)
                        this.imageURL = url.toString();
                } catch (MalformedURLException e) {
                    // changes imageURL
                }
            }
        }
    }

    //Returns variable imageURL
    public String getImageURL() {
        return imageURL;
    }
}
