package com.example.aishwarya.melotto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseFacebookUtils;

/**
 * Created by kborkar on 12/11/15.
 */
public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}
