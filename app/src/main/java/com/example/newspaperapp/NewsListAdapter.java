package com.example.newspaperapp;

/**
 * Created by MinhElQueue on 1/14/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<RssItem> {

    private Context context;
    private List<RssItem> items;
    private int layoutResourceId;

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
        imageView.setImageResource(R.drawable.observer_logo);
        textView.setText(items.get(position).getTitle());
        return rowView;
    }


}
