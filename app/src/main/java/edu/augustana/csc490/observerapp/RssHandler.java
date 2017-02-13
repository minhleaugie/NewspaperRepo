package edu.augustana.csc490.observerapp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BrightLight on 1/4/2017.
 */

public class RssHandler extends DefaultHandler{

    public static final String ITEM = "item";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LINK = "link";
    public static final String DATE = "pubdate";
    public static final String CATEGORY = "category";
    public static final String IMAGE_URL = "encoded";

    private RssItem item;
    private List<RssItem> itemList = new ArrayList<RssItem>();

    private boolean started = false;
    private StringBuilder sBuilder = new StringBuilder();

    /**
     * Finds an item tag to begin parsing of one story
     * Creates new RssItem that will be added to list
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equalsIgnoreCase(ITEM)) {
            item = new RssItem();
            started = true;
        }

    }

    /**
     * Parses through tags in Rss Feed
     * Compares them to string constants for tags title, description, link,
     * pubdate, category, and encoded (for the image_url)
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase(ITEM)) {
            itemList.add(item);
        } else if (started) {
            if (localName.equalsIgnoreCase(TITLE)) {
                item.setTitle(sBuilder.toString().trim());
            } else  if (localName.equalsIgnoreCase(DESCRIPTION)) {
                item.setDescription(sBuilder.toString().trim());
            } else  if (localName.equalsIgnoreCase(LINK)) {
                item.setLink(sBuilder.toString().trim());
            } else  if (localName.equalsIgnoreCase(DATE)) {
                item.setDate(sBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(CATEGORY)){
                item.addCategoryTag(sBuilder.toString().trim());
            } else if(localName.equalsIgnoreCase(IMAGE_URL)){
                item.setImageURL(sBuilder.toString().trim());
            }
            sBuilder = new StringBuilder();
        }
    }

    /**
     * Builds the string for each tag
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (started && (sBuilder != null)) {
            sBuilder.append(ch, start, length);
        }
    }

    /**
     * Retrieves and returns list of articles from feed
     * @return list of articles
     */
    public List<RssItem> getItemList() {
        return itemList;
    }
}
