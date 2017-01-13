package com.example.newspaperapp;

import java.util.HashMap;
import java.util.List;

/**
 * Created by BrightLight on 1/3/2017.
 */

public class Variables {

    //List of news site you want to add at home page
    public static final String[] NEWSPAPER = {"Augustana Observer"};
    public static final int[] ICONS =  {R.drawable.observer_logo};

    //Augustana College Observer
    public static final String[] AUGUSTANA_CATEGORIES = {"Home"};
    public static final String[] AUGUSTANA_LINKS = {"http://www.augustanaobserver.com/feed/"};

    //All
    public static final String[][] CATEGORIES = {AUGUSTANA_CATEGORIES};
    public static final String[][] LINKS = {AUGUSTANA_LINKS};

    public static final String NEWS = "news";
    public static final String CATEGORY = "category";
    public static final String LINK = "link";

    public static HashMap<Integer, List<RssItem>> newsMap = new HashMap<Integer, List<RssItem>>();



}
