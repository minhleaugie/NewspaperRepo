package com.example.newspaperapp;

import android.graphics.Bitmap;

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
    private Bitmap bitmap;
    private static int MIN_IMAGE_URL_LENGTH = (new String("http://www.augustanaobserver.com")).length();

    /**
     * Contructor for the RSS item
     */
    public RssItem() {
        tags = new ArrayList<String>();
    }

    /**
     * Retrieves and returns published data of article
     * @return String date of article
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the article published date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves and returns the title of the article
     * @return title of article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the article title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves and returns the link to the article
     * @return link of article
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link of the article
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Retrieves and returns the description of the article
     * @return description of the article
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the article
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds a tag to the array of tags that correspond to
     * the article
     * @param tag
     */
    public void addCategoryTag(String tag) { tags.add(tag); }

    /**
     * Retrieves and returns the array of tags for the article
     * @return array list of strings
     */
    public ArrayList<String> getCategoryTags() { return tags; }

    /**
     * Sets the image URL of the article and accounts for multiple pictures.
     * @param imageURL
     */
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

    /**
     * Retrieves and returns the URL for the images of the article.
     * @return URL of the image for the article
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the bitmap for the image of the article
     *
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Retrieves and returns the bitmap for the image of the article.
     *
     * @return bitmap of the image for the article
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}
