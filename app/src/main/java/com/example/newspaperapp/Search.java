package com.example.newspaperapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
 */

public class Search extends AppCompatActivity {

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
    }

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
            RssParser parser = new RssParser();
            return parser.getNewsList(urls[0]);
        }
        protected void onPostExecute(List<RssItem> items){
            dialog.dismiss();
            updateList(items);
        }
    }



    private void updateList(final List<RssItem> items){
        // create the adapter with layout from new_list_layout and the List<RssItem> items
        NewsListAdapter adapter = new NewsListAdapter(Search.this, R.layout.news_list_layout, items);

        ListView listView = (ListView) findViewById(R.id.search_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Search.this, ArticleActivity.class);
                intent.putExtra(Variables.LINK, items.get(position).getLink());
                startActivity(intent);
            }
        });
    }
}
