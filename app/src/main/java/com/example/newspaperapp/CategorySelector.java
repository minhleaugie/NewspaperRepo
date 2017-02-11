package com.example.newspaperapp;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(CategorySelector.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        break;

                    case R.id.action_category:
                        break;

                    case R.id.action_search:
                        Intent searchIntent = new Intent(CategorySelector.this, Search.class);
                        startActivity(searchIntent);
                        break;



                }
                return false;
            }
        });
    }

    public void openCategory(int linkChoice) {
        Intent intent = new Intent(CategorySelector.this, CategoryArticleView.class);
        intent.putExtra(Variables.LINK, linkChoice);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
