package edu.augustana.csc490.observerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The class is the main hub of the application. It displays the main
 * home page that contains a view pager with featured stories and a list
 * of the most recently published stories.
 */
public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    NewsListAdapter adapter;
    Timer timer;
    int page;
    private List<RssItem> items;
    private int scrollCount = 2;

    String token;

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
                    token = intent.getStringExtra("token");
                    //Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
                    //registers the app with the plugin on the wordpress website only once
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    if (!preferences.getBoolean("Registered", false)) {
                        NotificationRegistrationTask registrationTask = new NotificationRegistrationTask();
                        registrationTask.doInBackground();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("Registered", true).apply();

                    }
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                   // Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                   // Log.w("Testing", "didn't connect to GCM");
                } else {
                   // Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                }
            }
        };
        //checks if play services are available
        if (checkPlayServices()) {
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        customSwipeAdapter = new CustomSwipeAdapter(this, RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.FEATURES_FEED_INDEX], false));
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


        // instantiate bottom nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(MainActivity.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.main_out, R.anim.main_in);
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(MainActivity.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.main_out, R.anim.main_in);
                        break;
                }
                return false;
            }
        });

        // instantiate about page button
        ImageButton toAbout = (ImageButton) findViewById(R.id.toAbout);

        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutIntent = new Intent(MainActivity.this, About.class);
                startActivity(aboutIntent);
            }
        });

    }

    /**
     * Displays a message in Toast indication the various errors that could occur when
     * trying to register our app with Google Cloud Messaging. If there is not error,
     * return true
     * @return a boolean with the status of google play services
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not installed/enabled in this device!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support Google Play Services!", Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return true;
        }
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
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
                    if (page == 0) { // reset pageviewer to first image
                        viewPager.setCurrentItem(0);
                        page++;
                    } else if (page >= customSwipeAdapter.getCount()) { //reset to the beginning
                        page = 0;
                        viewPager.setCurrentItem(page);
                    } else { //move the pageviewer one forward
                        page = viewPager.getCurrentItem() + 1;
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
            });

        }
    }


    /**
     * This method creates a list view that
     * displays the most currently published.
     */
    public void makeNewsList() {

        //initiate the RssItem list from the Home
        //AUGUSTTANA_LINKS[0] is the first item in AUGUSTANA_LINKS, the feeds of Home page
        items = RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.MAIN_FEED_INDEX], false);

        //create the adapter with layout from new_list_layout and the List<RssItem> items
        adapter = new NewsListAdapter(this, R.layout.news_list_layout, items);

        //invoke the news list
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra(RssConstants.LINK, items.get(position).getLink());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //Gets more stories while users are still able to use app
        //new ShowMoreInScroll().execute();
    }

    private class NotificationRegistrationTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                //this should probably be a constant
                // URL url = new URL("http://www.augustanaobserver.com/wp-json/apnwp/register?os_type=android&device_token="+token);
                // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // conn.setRequestMethod("GET");

                // the proper code is above. We can only register to one plugin at a time so we
                // are using the demo website because we lack access to the actual website.
                URL demoURL = new URL("http://lovelace.augustana.edu/observerdemo/index.php/wp-json/apnwp/register?os_type=android&device_token=" + token);
                HttpURLConnection demoConn = (HttpURLConnection) demoURL.openConnection();
                demoConn.setRequestMethod("GET");

                if (demoConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //do input
                    response = "SUCCESS";
                    Toast.makeText(getApplicationContext(), "We did it!", Toast.LENGTH_LONG).show();
                    Log.w("Testing", "connected");
                } else {
                    //we failed yall
                    response = "FAIL";
                    Toast.makeText(getApplicationContext(), "We didn't do it!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }
            return response;
        }
    }

    private class ShowMoreInScroll extends AsyncTask<Void, Void, List<RssItem>> {
        @Override
        protected void onPreExecute() {
            System.out.println("ON PRE EXECUTE");
        }

        @Override
        protected List<RssItem> doInBackground(Void... voids) {
            return RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.MAIN_FEED_INDEX] + "?paged=" + scrollCount, false);
        }

        @Override
        protected void onPostExecute(List<RssItem> newItems) {
            items.addAll(newItems);
            scrollCount++;
            adapter.notifyDataSetChanged();
            System.out.println("ON POST EXECUTE");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.display_in, R.anim.display_out);
    }
}
