package com.example.newspaperapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class CategorySelector extends AppCompatActivity {
    ImageButton news, arts, features, opinions, sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_selector);

        news= (ImageButton) findViewById(R.id.newsButton);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(1);
            }
        });

        arts = (ImageButton) findViewById(R.id.artsButton);
        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(2);
            }
        });

        features = (ImageButton) findViewById(R.id.featuresButton);
        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(3);
            }
        });

        opinions = (ImageButton) findViewById(R.id.opinionsButton);
        opinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(4);
            }
        });

        sports = (ImageButton) findViewById(R.id.sportsButton);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(5);
            }
        });
    }

    public void openCategory(int linkChoice) {
        Intent intent = new Intent(CategorySelector.this, CategoryArticleView.class);
        intent.putExtra(Variables.LINK, linkChoice);
        startActivity(intent);
    }
}
