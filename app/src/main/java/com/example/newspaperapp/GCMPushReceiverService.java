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
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by andrewbainter13 on 12/9/2016.
 */

public class GCMPushReceiverService extends GcmListenerService {

    //this method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data){
        //getting the message from the bundle
        Bundle notification = (Bundle)data.get("notification");
        String message = notification.getString("body");
        //displaying a notification with the data
        sendNotification(message);
    }

    //this method is generating a notification and displaying the notification
    private void sendNotification(String message){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.observer_logo)
                .setContentText(message)
                .setSound(sound)
                .setContentTitle("Augustana Observer")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build(); //change minSDK in app level gradle, not sure of the repercussions
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification); //0 = ID of notification
    }

}
