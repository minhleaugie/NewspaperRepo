package com.example.newspaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by andrewbainter13 on 1/14/2017.
 */

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity = new Intent(StartScreen.this,Search.class);
                StartScreen.this.startActivity(mainActivity);
                StartScreen.this.finish();
            }
        }, 1500);
    }
}
