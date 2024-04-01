package com.example.sids_checklist.infopages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;

import java.util.Objects;

/**
 * This class represents the activity for displaying information about sleep locations.
 * It extends AppCompatActivity to provide compatibility with older Android versions.
 * The activity allows users to view information about sleep locations and includes a button to navigate back to the Info_Page_Activity.
 */
public class Sleep_Location_Activity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_location_layout);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button backButton = findViewById(R.id.InfoLocationBack);

        backButton.setOnClickListener(v-> startActivity(new Intent(Sleep_Location_Activity.this, Info_Page_Activity.class)));
    }
}
