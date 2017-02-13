package com.example.newspaperapp;

/**
 * Created by MinhElQueue on 1/14/2017.
 *
 * This will display a listview of the news articles with pictures, title, and date.
 * These can be clicked and redirects the user to the the appropriate article.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class NewsListAdapter extends ArrayAdapter<RssItem> {

    private Context context;
    private List<RssItem> items;
    private int layoutResourceId;
    private Drawable image;

    /**
     * This creates a new adapater and sets the global variables to the passed in parameters
     *
     * @param context the context in which to show the listview
     * @param layoutResourceId  the layout resource id
     * @param items an list of RSS items
     */
    public NewsListAdapter(Context context, int layoutResourceId, List<RssItem> items) {
        super(context, layoutResourceId, items);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.items = items;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_list_layout, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewIcon);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewNews);
        TextView pubText = (TextView) rowView.findViewById(R.id.pubDate);
        try {
            String imageURL = items.get(position).getImageURL();
            String thumbnailImageURL = getWordpressThumbnailURL(imageURL, "150x150");
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(thumbnailImageURL).getContent());
            if(bitmap != null) {
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 64,64,false));
            }
        } catch (Exception e) {

        }
        textView.setText(items.get(position).getTitle());
        String date = items.get(position).getDate().substring(0,17);
        pubText.setText(date);
        return rowView;
    }

    /**
     * Retrieves the thumbnail size version of the image supplied by wordpress.
     *
     * @param imageURL the url of the image to use
     * @param thumbSizing the size of the thumbnail
     * @return the new image url with the sizing appended on
     */
    static String getWordpressThumbnailURL(String imageURL, String thumbSizing) {
        int pos = imageURL.lastIndexOf('.');
        String extension = imageURL.substring(pos);
        String stem = imageURL.substring(0, pos);
        return stem+"-"+thumbSizing+extension;
    }

}
