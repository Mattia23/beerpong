package com.mirri.mirribilandia.beacon;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

public class ConfigurationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** TODO: Replace with your App ID and App Token.
         You can get them by adding a new app at https://cloud.estimote.com/#/apps
         */
        EstimoteSDK.initialize(getApplicationContext(), "gianluca-grossi2-studio-un-19c", "ed31732aa6de3b066ca213431136c97d");
        EstimoteSDK.enableDebugLogging(false);
    }
}
