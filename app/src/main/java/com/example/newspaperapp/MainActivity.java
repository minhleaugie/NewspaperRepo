package com.example.newspaperapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    Timer timer;
    int page;
    private List<RssItem> items;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        makeNewsList();

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

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        customSwipeAdapter = new CustomSwipeAdapter(this, items);
        viewPager.setAdapter(customSwipeAdapter);
        timer = new Timer();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                try {
                    timer.wait(10000);
                } catch (Exception e) {
                    //Throw an exception
                }

            }
        });
        page = 0;
        pageSwitcher(5);

    }

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

    public void pageSwitcher(int seconds) {
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    // this is an inner class...
    class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (page == 0) {
                        viewPager.setCurrentItem(0);
                        page++;
                    }else if (page >= customSwipeAdapter.getCount()) {
                        page = 0;
                        viewPager.setCurrentItem(page);
                    } else {
                        page = viewPager.getCurrentItem() + 1;
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
            });

        }
    }

    public void makeList(){
        ObjectItem[] data = (new DumbData()).getObjectItemData();

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_layout, data);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());

    }

    public void makeNewsList() {
        // create an RSS parser
        RssParser parser = new RssParser();

        //initiate the RssItem list from the Home
        //AUGUSTTANA_LINKS[0] is the first item in AUGUSTANA_LINKS, the feeds of Home page
        items = parser.getNewsList(Variables.AUGUSTANA_LINKS[0]);

        //create the adapter with layout from new_list_layout and the List<RssItem> items
        NewsListAdapter adapter = new NewsListAdapter(this, R.layout.news_list_layout, items);

        //invoke the news list
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra(Variables.LINK, items.get(position).getLink());
                startActivity(intent);


            }
        });

    }
}
