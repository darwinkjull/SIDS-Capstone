package com.example.sids_checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Checklist_SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // reference activity_checklist_splash.xml
        setContentView(R.layout.activity_checklist_splash);

        // Hide support action bar of activity
        getSupportActionBar().hide();

        // Splash Screen setup function
        // Intent in AndroidManifest.xml also set to have single launcher
        final Intent i = new Intent(Checklist_SplashActivity.this, Checklist_MainActivity.class);

        // Create a delay of 1500 milli for the launch of the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i); // begin splash display
                finish();
            }
        }, 1500);
    }
}