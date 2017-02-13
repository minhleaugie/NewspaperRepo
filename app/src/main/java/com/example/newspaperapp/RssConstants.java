package com.example.newspaperapp;

import java.util.HashMap;
import java.util.List;

/**
 * Created by BrightLight on 1/3/2017.
 */

public class RssConstants {



    //Augustana College Observer
    public static final String[] AUGUSTANA_CATEGORIES = {"Home"};
    public static final String[] AUGUSTANA_LINKS = {"http://www.augustanaobserver.com/feed/",
            "http://www.augustanaobserver.com/category/news/feed/",
            "http://www.augustanaobserver.com/category/arts-entertainment//feed/",
            "http://www.augustanaobserver.com/category/features/feed/",
            "http://www.augustanaobserver.com/category/opinions/feed/",
            "http://www.augustanaobserver.com/category/sports/feed/"};

    public static final int MAIN_FEED_INDEX = 0;
    public static final int NEWS_FEED_INDEX = 1;
    public static final int ARTS_ENTERTAINMENT_FEED_INDEX = 2;
    public static final int FEATURES_FEED_INDEX = 3;
    public static final int OPINIONS_FEED_INDEX = 4;
    public static final int SPORTS_FEED_INDEX = 5;



    public static final String CATEGORY = "category";
    public static final String LINK = "link";

    //public static HashMap<Integer, List<RssItem>> newsMap = new HashMap<Integer, List<RssItem>>();



}
