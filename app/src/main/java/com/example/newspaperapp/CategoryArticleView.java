package com.example.newspaperapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class CategoryArticleView extends AppCompatActivity {

    private List<RssItem> items;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_article_view);

        //initiate the RssItem list from the Home
        //AUGUSTTANA_LINKS[0] is the first item in AUGUSTANA_LINKS, the feeds of Home page
        int linkChoice = getIntent().getIntExtra(Variables.LINK,0);
        new CategoryArticleView.SearchListTask().execute(Variables.AUGUSTANA_LINKS[linkChoice]);
        dialog = new ProgressDialog(CategoryArticleView.this, ProgressDialog.STYLE_SPINNER).show(CategoryArticleView.this,"Getting Category...","");
    }


    private class SearchListTask extends AsyncTask<String, Void, List<RssItem>> {
        protected List<RssItem> doInBackground(String[] urls) {
            RssParser parser = new RssParser();
            return parser.getNewsList(urls[0], true);
        }
        protected void onPostExecute(List<RssItem> items){
            dialog.dismiss();
            updateList(items);
        }
    }

    /**
     * Updates the article list with articles based on search query
     * and displays them in a list view in a new activity.
     * @param items
     */
    private void updateList(final List<RssItem> items){
        // create the adapter with layout from new_list_layout and the List<RssItem> items
        NewsListAdapter adapter = new NewsListAdapter(CategoryArticleView.this, R.layout.news_list_layout, items);

        ListView listView = (ListView) findViewById(R.id.category_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CategoryArticleView.this, ArticleActivity.class);
                intent.putExtra(Variables.LINK, items.get(position).getLink());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}
