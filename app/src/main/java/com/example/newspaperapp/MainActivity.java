package com.example.newspaperapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity {

    private LinearLayout featStoryList;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            //We are sending the broadcast from GCMRegistrationIntentService
            //we may or may not want to do anything after registration completes
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                }
            }
        };

        if(checkPlayServices()){
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }

        //REFERENCE THE SCROLLABLE LAYOUT STRUCTURE IN MAIN_SCREEN.XML
        featStoryList = (LinearLayout) findViewById(R.id.linearList);

        //FILL THE SCROLLABLE LAYOUT STRUCTURE WITH FEATURED STORIES
        fillFeaturedCarousel();
    }

    private void fillFeaturedCarousel() {
        // POPULATE THE LINEAR LIST CAROUSEL WITH FEATURED STORIES
        ImageButton buttonItem;

        for (int i = 0; i < FeaturedStoryDatabase.description.length; i++) {
            //STORE THE INDIVIDUAL PAINTINGS AS BUTTONS
            buttonItem = new ImageButton(this);


            Story story = new Story(FeaturedStoryDatabase.description[i], FeaturedStoryDatabase.id[i]);

            //USE THE CONTENT DESCRIPTION PROPERTY TO STORE
            //STORY DATA

            buttonItem.setContentDescription(story.getDescription());

            //LOAD THE STORY USING ITS UNIQUE ID

            buttonItem.setImageDrawable(getResources().getDrawable(
                    story.getId()));

            //SET AN ONCLICK LISTENER FOR THE IMAGE BUTTON
            buttonItem.setOnClickListener(retrieveStory);

            //ADD THE IMAGE BUTTON TO THE SCROLLABLE LINEAR LIST
            featStoryList.addView(buttonItem);
        }
    }

    private View.OnClickListener retrieveStory = new View.OnClickListener() {
        public void onClick(View btn) {
            // COLLECT THE INFORMATION STORED ABOUT THE STORY
            String paintingDescription = (String) btn.getContentDescription();

            // TODO: MAKE A METHOD CALL TO RETRIEVE THE STORY

        }
    };

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(), "Google Play Service is not installed/enabled in this device!", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(getApplicationContext(), "This device does not support Google Play Services!", Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return true;
        }
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume(){
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    //Unregistering receiver on activity paused
    @Override
    protected void onPause(){
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
}
