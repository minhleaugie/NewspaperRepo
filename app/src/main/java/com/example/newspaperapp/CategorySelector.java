package com.example.newspaperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class CategorySelector extends AppCompatActivity {
    ImageView sports, news, arts, features, opinions;
    ArrayList<RssItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);
        items = (ArrayList<RssItem>) getIntent().getSerializableExtra("mylist");

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == findViewById(R.id.newsImage)){
                    openCategory(1);
                }
                if (view == findViewById(R.id.artsImage)){
                    openCategory(2);
                }
                if (view == findViewById(R.id.featuresImage)){
                    openCategory(3);
                }
                if (view == findViewById(R.id.opinionsImage)){
                    openCategory(4);
                }
                if (view == findViewById(R.id.sportsImage)){
                    openCategory(5);
                }

            }
        });
    }

    public void openCategory(int linkChoice) {
        Intent intent = new Intent(CategorySelector.this, CategoryArticleView.class);
        intent.putExtra(Variables.LINK, Variables.AUGUSTANA_LINKS[linkChoice]);
        startActivity(intent);
    }
}
