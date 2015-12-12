package com.example.aishwarya.melotto;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by kborkar on 12/11/15.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "XNAyB2GPqyzKkeKZWtuWwHCwEG3B42aJOKaI05cT", "idpLsnS9aRGDctmVTvFT8EwexVlsSvEXadOOpC0E");
        ParseFacebookUtils.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
