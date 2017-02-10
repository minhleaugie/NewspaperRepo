package com.example.newspaperapp;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by BrightLight on 1/4/2017.
 */

public class RssParser {

    List<RssItem> cachedRssItems = null;
    Date lastDownloadTime;

    public List<RssItem> getNewsList(String link, boolean forceFreshDownload) {
        Date now = new Date();
        // use cached time if less than 30 minutes since last download
        if (cachedRssItems != null && !forceFreshDownload && getDateDiff(lastDownloadTime,now,TimeUnit.MINUTES) < 30) {
            return cachedRssItems;
        } else {
            try {
                URL url = new URL(link);
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                RssHandler handler = new RssHandler();
                reader.setContentHandler(handler);
                InputSource source = new InputSource(url.openStream());
                reader.parse(source);
                cachedRssItems = handler.getItemList();
                lastDownloadTime = now;
                return cachedRssItems;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }


    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     *
     * Courtesy of: http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
