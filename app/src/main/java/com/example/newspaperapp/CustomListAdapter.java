package com.example.newspaperapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ver on 1/8/2017.
 */

public class CustomListAdapter extends ArrayAdapter<ObjectItem> {

    Context ctx;
    int layoutResourceId;
    ObjectItem data[] = null;

    public CustomListAdapter(Context ctx, int layoutResourceId, ObjectItem[] data){
        super(ctx, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        ObjectItem objectItem = data[position];

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.imageViewItem);
        imageViewItem.setImageResource(objectItem.itemImage);

        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(objectItem.itemName);
        textViewItem.setTag(objectItem.itemId);

        return convertView;
    }


}
