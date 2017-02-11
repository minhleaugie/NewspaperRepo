package com.example.newspaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by andrewbainter13 on 2/11/2017.
 */

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_screen);

        TextView textView = (TextView) findViewById(R.id.about_textview);

        textView.setText("We're super awesome computer scientists.");


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(About.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(About.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.main_in, R.anim.main_out);
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(About.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        break;
                }
                return false;
            }
        });

    }
}
