package com.example.newspaperapp;

/**
 * Created by Ver on 1/6/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.List;

/**
 * Created by andrewbainter13 on 12/9/2016.
 */

public class GCMPushReceiverService extends GcmListenerService {

    String message;
    String title;
    String url;


    /**
     * This method is called on every new message received
     * @param from
     * @param data
     */
    @Override
    public void onMessageReceived(String from, Bundle data){
        //getting the message from the bundle
        openBundle(data);
        message = data.getString("message");
        title = data.getString("title");
        //displaying a notification with the data

        Log.w("Testing", "do we even get here?");


        //get the corresponding article
        new FindURLTask().execute(RssConstants.AUGUSTANA_LINKS[RssConstants.MAIN_FEED_INDEX]);

        //sendNotification(message, title, smallIcon);
        Log.w("Testing", "do we even get here? after async");
    }

    /**
     * This method opens the bundle of information from GCM
     * @param bun
     */
    private void openBundle(Bundle bun){
        if(bun == null){
            return;
        }
        for(String key: bun.keySet()){
            Log.w("DaBundle", key);
        }
    }

    /**
     * This method generates a notification and displays the notification
     * @param message
     * @param title
     */
    private void sendNotification(String message, String title){
        Intent intent;
        if(url != null) {
            intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(RssConstants.LINK, url);
        } else {
            intent = new Intent(this, StartScreen.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
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
        notificationManager.notify(0, notification); //0 = ID of notification
    }

    private class FindURLTask extends AsyncTask<String, Void, List<RssItem>> {
        protected List<RssItem> doInBackground(String[] urls) {
            return RssParser.getNewsList(urls[0], true);
        }
        protected void onPostExecute(List<RssItem> items){
            url = items.get(0).getLink();
            for(RssItem item: items){
                if(item.getTitle().equalsIgnoreCase(title)){
                    url=item.getLink();
                    sendNotification(message, title);
                    return;
                }
            }
            sendNotification(message, title);
        }
    }

}
