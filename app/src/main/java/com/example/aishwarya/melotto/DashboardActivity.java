package com.example.aishwarya.melotto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String welcome = getIntent().getExtras().getString("name");
        Log.d("Welcome", welcome);
        username = (TextView) findViewById(R.id.txt_name);
        username.setText(welcome);
    }
}
