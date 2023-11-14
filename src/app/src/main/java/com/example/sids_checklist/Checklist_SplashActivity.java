package com.example.sids_checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class Checklist_SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // reference activity_checklist_splash.xml
        setContentView(R.layout.activity_checklist_splash);

        // Hide support action bar of activity
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Splash Screen setup function
        // Intent in AndroidManifest.xml also set to have single launcher
        final Intent i = new Intent(Checklist_SplashActivity.this,
                Pin_Activity.class);

        // Create a delay of 1500 milli for the launch of the splash screen
        new Handler().postDelayed(() -> {
            startActivity(i); // begin splash display
            finish();
        }, 1500);
    }
}