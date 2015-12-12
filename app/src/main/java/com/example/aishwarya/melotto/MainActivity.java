package com.example.aishwarya.melotto;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        Parse.enableLocalDatastore(this);
//
//        Parse.initialize(this, "XNAyB2GPqyzKkeKZWtuWwHCwEG3B42aJOKaI05cT", "idpLsnS9aRGDctmVTvFT8EwexVlsSvEXadOOpC0E");
//

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        Button mBtnFb = (Button) findViewById(R.id.fbBtn);
        final List<String> permissions = Arrays.asList("public_profile", "email");
        mBtnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                            Toast.makeText(getApplicationContext(), "Log-out from Facebook and try again please!", Toast.LENGTH_SHORT).show();
                            //ParseUser.logOut();
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
//                            if (!ParseFacebookUtils.isLinked(user)) {
//                                ParseFacebookUtils.linkWithReadPermissionsInBackground(user, MainActivity.this, permissions, new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException ex) {
//                                        if (ParseFacebookUtils.isLinked(user)) {
//                                            Log.d("MyApp", "Woohoo, user logged in with Facebook!");
//
////                                            proDialog.hide();
//                                        }
//                                    }
//                                });
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "You can change your personal data in Settings tab!", Toast.LENGTH_SHORT).show();
//                            }

                            //getUserDetailsFromFB();
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            //getUserDetailsFromParse();
//                            if (!ParseFacebookUtils.isLinked(user)) {
//                                ParseFacebookUtils.linkWithReadPermissionsInBackground(user, MainActivity.this, permissions, new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException ex) {
////                                        if (ParseFacebookUtils.isLinked(user)) {
////                                            Log.d("MyApp", "Woohoo, user logged in with Facebook!");
////
//////                                            proDialog.hide();
////                                        }
//                                    }
//                                });
//                            }
//                            else{
////                                proDialog.hide();
//                            }
                        }
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getApplicationContext());

    }


    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getApplicationContext());
    }


}
