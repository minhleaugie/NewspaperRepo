package com.example.newspaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by andrewbainter13 on 2/11/2017.
 * Displays about screen.
 */

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_screen);

        // instantiates text views
        TextView titleText = (TextView) findViewById(R.id.title);
        TextView devText = (TextView) findViewById(R.id.developers);
        TextView supText = (TextView) findViewById(R.id.supervisor);
        TextView ackText = (TextView) findViewById(R.id.acknowledgments);

        // set text views text
        titleText.setText("Augustana Observer App Version 1.0.0");
        devText.setText("Developers: Andrew Bainter, Lauren Johnson, Riley Moss, Minh Le");
        supText.setText("Supervised by: Dr. Forrest Stondahl");
        ackText.setText("Acknowledments: The Augustana Observer Faculty, Firebase, and Google Communication Services");


        // instantiate bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        // set bottom nav bar items
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(About.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(About.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.main_in, R.anim.main_out);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(About.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        break;
                }
                return false;
            }
        });

    }
}
