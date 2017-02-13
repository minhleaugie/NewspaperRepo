package com.example.newspaperapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by BrightLight on 1/4/2017.
 *
 * Use a URL passed into the intent to show the observer webpage in a webview.
 * Sets a loading dialog to let the user know it is retrieving the data
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
        link = bundle.getString(RssConstants.LINK);
        webView = (WebView) findViewById(R.id.webViewNews);

        webView.setWebViewClient(new ArticleWebViewClient());

        dialog = ProgressDialog.show(this, "", "Loading ...");
        webView.loadUrl(link);

        //instanstiate bottom nav bar
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
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    case R.id.action_category:
                        Intent categoryIntent = new Intent(ArticleActivity.this, CategorySelector.class);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(ArticleActivity.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }
                return false;
            }
        });

    }

    /**
     * Sets the webview to the article we obtained prior and closes the dialog
     */

    class ArticleWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if(dialog!=null) {
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}

