package com.example.newspaperapp;

/**
 * Created by Ver on 1/6/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by andrewbainter13 on 12/9/2016.
 */

public class GCMPushReceiverService extends GcmListenerService {

    //this method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data){
        //getting the message from the bundle
        openBundle(data);
        String message = data.getString("message");
        String title = data.getString("title");
        int smallIcon = data.getInt("smallIcon",R.drawable.observer_logo);
        //displaying a notification with the data
        sendNotification(message, title, smallIcon);
    }

    private void openBundle(Bundle bun){
        if(bun == null){
            return;
        }
        for(String key: bun.keySet()){
            Log.w("DaBundle", key);
        }
    }

    //this method is generating a notification and displaying the notification
    private void sendNotification(String message, String title, int smallIcon){
        Intent intent = new Intent(this, StartScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(smallIcon)
                .setContentText(message)
                .setSound(sound)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build(); //change minSDK in app level gradle, not sure of the repercussions
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification); //0 = ID of notification
    }

}
