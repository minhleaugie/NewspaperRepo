package com.example.newspaperapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CategorySelector extends AppCompatActivity {
    //ImageView sports, news, arts, features, opinions;
    LinearLayout news, arts, features, opinions, sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_selector);
/**
        news= (LinearLayout) findViewById(R.id.newsLayout);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(1);
            }
        });

        arts = (LinearLayout) findViewById(R.id.artsLayout);
        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(2);
            }
        });

        features = (LinearLayout) findViewById(R.id.featuresLayout);
        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(3);
            }
        });

        opinions = (LinearLayout) findViewById(R.id.opinionsLayout);
        opinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(4);
            }
        });

        sports = (LinearLayout) findViewById(R.id.sportsLayout);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(5);
            }
        });
 **/
    }

    public void openCategory(int linkChoice) {
        Intent intent = new Intent(CategorySelector.this, CategoryArticleView.class);
        intent.putExtra(Variables.LINK, linkChoice);
        startActivity(intent);
    }
}
