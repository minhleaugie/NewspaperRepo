package com.example.newspaperapp;

/**
 * Created by Ver on 1/6/2017.
 */
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class GCMTokenRefreshListenerService extends InstanceIDListenerService {

    //if the token is changed registering the device again
    @Override
    public void onTokenRefresh(){
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
