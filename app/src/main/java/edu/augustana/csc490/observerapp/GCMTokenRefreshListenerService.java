package edu.augustana.csc490.observerapp;

/**
 * Created by Ver on 1/6/2017.
 *
 * TODO: Include Javadoc for the CLASS here.
 */
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class GCMTokenRefreshListenerService extends InstanceIDListenerService {

    /**
     * Registers the device again if the token is changed
     */
    @Override
    public void onTokenRefresh(){
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
