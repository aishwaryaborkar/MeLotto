package com.example.aishwarya.melotto;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by kborkar on 1s2/11/15.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(getApplicationContext());

        Parse.initialize(this, "XNAyB2GPqyzKkeKZWtuWwHCwEG3B42aJOKaI05cT", "idpLsnS9aRGDctmVTvFT8EwexVlsSvEXadOOpC0E");
        ParseFacebookUtils.initialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));


    }
}
