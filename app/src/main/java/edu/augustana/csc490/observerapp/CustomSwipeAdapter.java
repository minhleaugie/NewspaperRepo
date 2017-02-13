package edu.augustana.csc490.observerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newspaperapp.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Ver on 1/7/2017.
 *
 * Uses information from the RSS feed to populate a pageviewer that allows for swiping
 * through images on the home screen. Sets the appropriate text with the pictures to
 * explain what the pictures are. Sets a listener on each picture so clicking the picture
 * will take the user to the accompanying article
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
            R.drawable.dog5, R.drawable.dog6, R.drawable.dog7};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<RssItem> items;
    private int position;
    private Drawable image;

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
        this.position=position;
        //removes erroneous data from the items array
        if(title.equals("Augustana Observer") || title.equals("title")){
            items.remove(position);
            title = items.get(position).getTitle();
        }
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(items.get(position).getImageURL()).getContent());
            if(bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {

        }
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
            Intent intent = new Intent(ctx, ArticleActivity.class);
            intent.putExtra(RssConstants.LINK, items.get(position).getLink());
            ctx.startActivity(intent);
        }
    };

}
