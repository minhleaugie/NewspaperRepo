package com.example.newspaperapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.setWebViewClient(new ArticleWebViewClient());

        dialog = ProgressDialog.show(this, "", "Loading ...");
        webView.loadUrl(link);

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
}

