package com.example.newspaperapp;

/**
 * Created by Riley on 12/20/2016.
 */

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class HandleXML {
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    private ArrayList<XMLItem> items;

    public HandleXML(String url) {
        this.urlString = url;
    }

    public ArrayList<XMLItem> getItem(){ return items;}

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        items = new ArrayList<>();
        XMLItem item = new XMLItem();

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = "";
                name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case "title":
                                item.setTitle(text);
                                break;
                            case "link":
                                item.setLink(text);
                                break;
                            case "description":
                                item.setDescription(text);
                                break;
                            case "img":
                                item.setUrl(myParser.getAttributeValue(null, "src"));
                                break;
                            default:
                                break;
                        }

                        break;
                }
                event = myParser.next();
                if(name!=null) {
                    if (name.equals("item")) {
                        items.add(item);
                        item = new XMLItem();
                    }
                }
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }

}