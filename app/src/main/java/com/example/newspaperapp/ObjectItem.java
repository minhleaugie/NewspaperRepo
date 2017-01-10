package com.example.newspaperapp;

/**
 * Created by Ver on 1/8/2017.
 */

public class ObjectItem {

    public int itemId;
    public String itemName;
    public int itemImage;
    public String itemLink;

    public ObjectItem(int itemId, String itemName, int itemImage){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        itemLink = "http://www.google.com";
    }
}
