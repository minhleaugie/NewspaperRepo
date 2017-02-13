package com.example.newspaperapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by andrewbainter13 on 1/14/2017.
 * Creates timed start sceen for opening of application.
 */

public class StartScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // pre-load the RSS feeds so they are cached, and the home screen will show quickly...
                RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.MAIN_FEED_INDEX], false);
                RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.FEATURES_FEED_INDEX], false);


                Intent mainActivity = new Intent(StartScreen.this,MainActivity.class);
                StartScreen.this.startActivity(mainActivity);
                StartScreen.this.finish();
            }
        });

//        // Creates a timed start screen and starts main activity once time is up
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainActivity = new Intent(StartScreen.this,MainActivity.class);
//                StartScreen.this.startActivity(mainActivity);
//                StartScreen.this.finish();
//            }
//        }, 1000);
    }
}
