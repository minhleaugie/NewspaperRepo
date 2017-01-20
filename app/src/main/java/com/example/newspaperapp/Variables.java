package com.example.newspaperapp;

import java.util.HashMap;
import java.util.List;

/**
 * Created by BrightLight on 1/3/2017.
 */

public class Variables {



    //Augustana College Observer
    public static final String[] AUGUSTANA_CATEGORIES = {"Home"};
    public static final String[] AUGUSTANA_LINKS = {"http://www.augustanaobserver.com/feed/",
            "http://www.augustanaobserver.com/category/news/feed/",
            "http://www.augustanaobserver.com/category/arts-entertainment//feed/",
            "http://www.augustanaobserver.com/category/features/feed/",
            "http://www.augustanaobserver.com/category/opinions/feed/",
            "http://www.augustanaobserver.com/category/sports/feed/"};





    public static final String CATEGORY = "category";
    public static final String LINK = "link";

    public static HashMap<Integer, List<RssItem>> newsMap = new HashMap<Integer, List<RssItem>>();



}
