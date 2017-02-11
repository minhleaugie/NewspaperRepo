package com.example.newspaperapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by BrightLight on 1/4/2017.
 */

public class ArticleActivity extends Activity {

    private WebView webView;
    private String link;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Bundle bundle = getIntent().getExtras();
        setTitle(R.string.app_name);
        link = bundle.getString(Variables.LINK);
        webView = (WebView) findViewById(R.id.webViewNews);

        webView.setWebViewClient(new ArticleWebViewClient());

        dialog = ProgressDialog.show(this, "", "Loading ...");
        webView.loadUrl(link);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(ArticleActivity.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(ArticleActivity.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(ArticleActivity.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        break;
                }
                return false;
            }
        });

    }

    class NewTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;

        }


    }

    class ArticleWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if(dialog!=null) {
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }

    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}

