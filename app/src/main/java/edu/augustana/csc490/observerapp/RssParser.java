package edu.augustana.csc490.observerapp;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by BrightLight on 1/4/2017.
 *
 * TODO: Include Javadoc for the CLASS here.
 */

public class RssParser {

    // data fields
    private static Map<String,List<RssItem>> cachedRssItems = new HashMap<>();
    private static Map<String,Date> lastDownloadTimes = new HashMap<>();

    // TODO: Delete this after compiling
    private RssParser() {

    }
    /**
     * Get the list of articles from the provided link to the
     * RSS feed.
     * @param link
     * @param forceFreshDownload
     * @return list of articles
     */
    public static List<RssItem> getNewsList(String link, boolean forceFreshDownload) {
        Log.d("NEWSIES", "RssParser got request (force="+forceFreshDownload+"): " + link);

        Date now = new Date();
        // use cached time if less than 30 minutes since last download
        if (cachedRssItems.containsKey(link) && !forceFreshDownload && getDateDiff(lastDownloadTimes.get(link),now,TimeUnit.MINUTES) < 30) {
            Log.d("NEWSIES", "RssParser returning cached copy.");
            return cachedRssItems.get(link);
        } else {
            try {
                Log.d("NEWSIES", "RssParser fetching new copy.");
                URL url = new URL(link);
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                RssHandler handler = new RssHandler();
                reader.setContentHandler(handler);
                InputSource source = new InputSource(url.openStream());
                reader.parse(source);
                List<RssItem> retrievedItems = handler.getItemList();
                Log.d("NEWSIES", "RssParser: " + retrievedItems);
                cachedRssItems.put(link,retrievedItems );
                lastDownloadTimes.put(link, now);
                return retrievedItems;
            } catch (Exception e) {
                Log.d("NEWSIES", "GOT ERROR: " + e);
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
