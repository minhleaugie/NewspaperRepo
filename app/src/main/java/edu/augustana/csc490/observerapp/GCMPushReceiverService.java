package edu.augustana.csc490.observerapp;

/**
 * Created by Ver on 1/6/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.newspaperapp.R;
import com.google.android.gms.gcm.GcmListenerService;

import java.util.List;

/**
 * Created by andrewbainter13 on 12/9/2016.
 */

public class GCMPushReceiverService extends GcmListenerService {

    /**
     * This method will store the notificationID in shared preferences
     * This app will only last for 58,000 years using this method if the
     * assuming the Observer sends 100 posts a day.
     *
     * @param from the string of the sender
     * @param data the bundle of data
     */
    //this method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GCMPushReceiverService.this);
        int notificationID = preferences.getInt("NotificationID", 0);
        SharedPreferences.Editor editor = preferences.edit();
        notificationID++;
        editor.putInt("NotificationID", notificationID).apply();

        //get the corresponding article
        new FindURLTask( data.getString("message"), data.getString("title"),notificationID).execute(RssConstants.AUGUSTANA_LINKS[RssConstants.MAIN_FEED_INDEX]);

    }

    /**
     * This method generates the notification and displays it. It allows for multiple notifications
     * to stack up and direct to the appropriate article when the notifications are clicked
     *
     * @param message the message to show in the notification
     * @param title the title to show with notification
     * @param url the individual url that will take you to the observer webview
     * @param notificationID the unique id to allow multiple notifications
     */
    private void sendNotification(String message, String title, String url, int notificationID){
        Intent intent;
        if(url != null) {
            intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(RssConstants.LINK, url);
        } else {
            intent = new Intent(this, StartScreen.class);
        }
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = notificationID;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.observer_logo)
                .setContentText(message)
                .setSound(sound)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build(); //change minSDK in app level gradle, not sure of the repercussions
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification); //0 = ID of notification
    }

    /**
     * This class finds the URL in a separate AsyncTask before showing a notification
      */
    private class FindURLTask extends AsyncTask<String, Void, List<RssItem>> {

        private String message;
        private String title;
        private int notificationID;

        /**
         *
         * Constructor
         *
         * @param message the body of the post to show in the notification
         * @param title the title of the post to show in the notificaition
         * @param notificationID the unique id to allow multiple notifications
         */
        public FindURLTask(String message, String title, int notificationID){
            super();
            this.message = message;
            this.title = title;
            this.notificationID = notificationID;
        }

        protected List<RssItem> doInBackground(String[] urls) {
            // we download the feature list RSS here, even though we don't need it for handling the
            // notification, but this way we'll have a cached copy so the home screen will load
            // quickly if they go back to home after reading this article...
            RssParser.getNewsList(RssConstants.AUGUSTANA_LINKS[RssConstants.FEATURES_FEED_INDEX], false);

            return RssParser.getNewsList(urls[0], true);
        }
        protected void onPostExecute(List<RssItem> items){
            String url = items.get(0).getLink();
            // find the article with the matching title and use that URL
            for(RssItem item: items){
                if(stripSpecialChars(item.getTitle()).equalsIgnoreCase(stripSpecialChars(title))){
                    url=item.getLink();
                    sendNotification(message, title, url, notificationID);
                    return;
                }
            }
            sendNotification(message, title, url, notificationID);
        }
    }

    /**
     * In order to compare the titles, we want to remove any special characters that
     * may be in the received title. This method does that.
     *
     * @param original - original text
     * @return the same text with only alphanumeric characters (all others removed)
     */
    public String stripSpecialChars(String original) {
        return original.replaceAll("[^A-Za-z0-9]", "");
    }

}
