package com.example.newspaperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout featStoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
