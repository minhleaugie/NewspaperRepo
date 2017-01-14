package com.example.newspaperapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ver on 1/7/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
            R.drawable.dog5, R.drawable.dog6, R.drawable.dog7};
    private String reallyBigString = "this is a really big String. let's see if the text will wrap or if the app will explode. please don't explode, though";
    private String[] image_names = {"This dog is awesome", "This dog is awesome, too", "blahblah", reallyBigString, "how big is this array", "it's 7","this is the last"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<RssItem> items;

    public CustomSwipeAdapter(Context ctx, List<RssItem> items){
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String title = items.get(position).getTitle();
        if(title.equals("Augustana Observer") || title.equals("title")){
            items.remove(position);
            title = items.get(position).getTitle();
        }
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        imageView.setImageResource(image_resources[position]);
        imageView.setOnClickListener(listener);
        Button button = (Button) item_view.findViewById(R.id.text_view);
        button.setText(title);
        button.setOnClickListener(listener);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(ctx,"You clicked an awesome dog", Toast.LENGTH_LONG).show();
        }
    };
}
