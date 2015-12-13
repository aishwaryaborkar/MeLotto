package com.example.aishwarya.melotto;


import android.app.Activity;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends Activity {



    Button mBtnFb;
    TextView mUsername, mEmailID;
    Profile mFbProfile;
    ParseUser parseUser;
    String name = null, email = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.aishwarya.melotto",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        mBtnFb = (Button) findViewById(R.id.btn_fb_login);
        //mProfileImage = (CircleImageView) findViewById(R.id.profile_image);

        mUsername = (TextView) findViewById(R.id.txt_name);
        mEmailID = (TextView) findViewById(R.id.txt_email);

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
                            getUserDetailsFromFB();

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


                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");

                            getUserDetailsFromParse();
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

    private void getUserDetailsFromFB() {
        new GraphRequest(

                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
           /* handle the result */
                        try {
                            email = response.getJSONObject().getString("email");
                           // mEmailID.setText(email);
                            name = response.getJSONObject().getString("name");
                           //mUsername.setText(name);
                            saveNewUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
        ProfilePhotoAsync profilePhotoAsync = new ProfilePhotoAsync(mFbProfile);
        profilePhotoAsync.execute();
    }

    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(name);
        parseUser.setEmail(email);
//        Saving profile photo as a ParseFile
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
       // bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        //byte[] data = stream.toByteArray();
        //String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
       // final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
//       parseFile.saveInBackground(new SaveCallback() {
//           @Override
//           public void done(ParseException e) {
//               parseUser.put("profileThumb", parseFile);
//               //Finally save all the user details
//
//          }
//      });

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(MainActivity.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();
//Fetch profile photo
//        try {
//            ParseFile parseFile = parseUser.getParseFile("profileThumb");
//            byte[] data = parseFile.getData();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            mProfileImage.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mEmailID.setText(parseUser.getEmail());
        mUsername.setText(parseUser.getUsername());
        Toast.makeText(MainActivity.this, "Welcome back " + mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
    }


}
