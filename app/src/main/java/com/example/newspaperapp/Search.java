package com.example.newspaperapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Ver on 1/16/2017.
 * Searches RSS feed based on user input and displays related stories.
 */

public class Search extends AppCompatActivity {

    // data fields
    private static final String OBSERVER_SEARCH_URL="http://www.augustanaobserver.com/?s=";

    private EditText search;
    private String stringSearch = "Search . . .";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.search_layout);
        
        search = (EditText)findViewById(R.id.search);

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    search.setText("");
                }else {
                    search.setText(stringSearch);
                }
            }
        });

        // attached listener to edit text field to detect changes to search query
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    String searchMe = buildString(search.getText().toString());
                    new SearchListTask().execute(searchMe);
                    dialog = new ProgressDialog(Search.this, ProgressDialog.STYLE_SPINNER).show(Search.this,"Searching", "Getting search results");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });

        // instantiate bottom nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(Search.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(Search.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.display_in, R.anim.display_out);
                        break;
                    case R.id.action_search:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * This method takes the users search query, builds and returns the String of
     * URL for the RSS feed.
     * @param query
     * @return String URL for RSS feed based on user search input
     */
    private String buildString(String query){
        try{
            String unreliableSource = URLEncoder.encode(query, "utf-8");
            stringSearch=OBSERVER_SEARCH_URL+unreliableSource+"&feed=rss";
        } catch (Exception e){
        }
        return stringSearch;
    }


    private class SearchListTask extends AsyncTask<String, Void, List<RssItem>> {
        protected List<RssItem> doInBackground(String[] urls) {

            return RssParser.getNewsList(urls[0], true);
        }
        protected void onPostExecute(List<RssItem> items){
            dialog.dismiss();
            updateList(items);
        }
    }


    /**
     * This method updates the list of stories with the queried stories.
     * @param items
     */
    private void updateList(final List<RssItem> items){
        // create the adapter with layout from new_list_layout and the List<RssItem> items
        NewsListAdapter adapter = new NewsListAdapter(Search.this, R.layout.news_list_layout, items);

        ListView listView = (ListView) findViewById(R.id.search_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Search.this, ArticleActivity.class);
                intent.putExtra(RssConstants.LINK, items.get(position).getLink());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.main_out, R.anim.main_in);
    }

}
